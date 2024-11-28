package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Registration;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean add(Payment entity) throws IOException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity); // Saving the Payment entity
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String generateNewID() throws IOException {
       /* try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            // Query to fetch the last inserted paymentId
            Query query = session.createQuery("SELECT paymentId FROM Payment ORDER BY paymentId DESC");
            query.setMaxResults(1);
            List results = query.list();

            transaction.commit();
            return (results.isEmpty()) ? null : (String) results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT paymentId FROM Payment ORDER BY paymentId DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public List<Payment> getAll() throws IOException {
        List<Payment> resultList = new ArrayList<>();
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            NativeQuery query = session.createNativeQuery("SELECT * FROM Payment");
            query.addEntity(Payment.class);
            resultList = query.getResultList(); // Fetching all Payments

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public boolean update(Payment entity) throws IOException {
        // Implement update functionality if needed
        return false;
    }

    @Override
    public boolean delete(String id) throws IOException {
        // Implement delete functionality if needed
        return false;
    }

    @Override
    public List<Registration> getRegistrationIds() {
        List<Registration> registrationIds = new ArrayList<>();
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM Registration";
            Query<Registration> query = session.createQuery(hql, Registration.class);
            registrationIds = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registrationIds;
    }

    double paidAmount = 0;
    @Override
    public double getPaidAmountByRegistrationId(Registration registrationId) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Query to get the payment amount by registration ID
            String hql = "SELECT r.Payment FROM Registration r WHERE r.regiId = :regiId";
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("regiId", registrationId.getRegiId());
            paidAmount = query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paidAmount;
    }

    double fullFee = 0;
    @Override
    public double getFullFeeByRegistrationId(Registration selectedRegistrationId) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Corrected query for getting full fee for the program
            String hql = "SELECT prog.fee FROM Registration reg " +
                    "JOIN reg.programme prog " +
                    "WHERE reg.regiId = :regiId";
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("regiId", selectedRegistrationId.getRegiId());
            fullFee = query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullFee;
    }

    @Override
    public double getAmountRegistrationId(Registration selectedRegistrationId) {
        double amount = getFullFeeByRegistrationId(selectedRegistrationId) - getPaidAmountByRegistrationId(selectedRegistrationId);
        return amount; // Calculate remaining amount
    }
}
