package lk.ijse.dao.custom.impl;


import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.entity.Registration;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public boolean add(Registration entity) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);


        transaction.commit();
        session.close();


        return false;
    }

    @Override
    public String generateNewID() throws IOException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT regiId FROM Registration ORDER BY regiId DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();
      //  int x= (int) results.get(0);
      //  System.out.println(results.get(0));
        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public List<Registration> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Registration> list = session.createQuery("from Registration ", Registration.class).list();


        transaction.commit();
        session.close();


        return list;
    }

    @Override
    public boolean update(Registration entity) throws IOException {
        return false;
    }

    @Override
    public boolean delete(String id) throws IOException {
        return false;
    }
}
