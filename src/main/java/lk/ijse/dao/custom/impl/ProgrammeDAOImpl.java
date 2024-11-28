package lk.ijse.dao.custom.impl;


import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.entity.Programme;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeDAOImpl implements ProgrammeDAO {

    @Override
    public boolean add(Programme programme) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(programme);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT programmeId FROM Programme ORDER BY programmeId DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public List<Programme> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Programme");
        nativeQuery.addEntity(Programme.class);
        List<Programme> programmes= nativeQuery.list();


        transaction.commit();
        session.close();

        return programmes;
    }

    @Override
    public boolean update(Programme programme) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(programme);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Programme programme = new Programme();
        programme.setProgrammeId(id);
        session.remove(programme);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<Programme> SearchCID(String cid) throws IOException {
        List<Programme> courseList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            courseList = session.createQuery("FROM Programme WHERE programmeId = :cid", Programme.class)
                    .setParameter("cid", cid)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return courseList;
    }

    @Override
    public Programme searchByCId(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Programme course = null;

        try {
            transaction = session.beginTransaction();

            // find Customer by ID
            String hql = "FROM Programme WHERE programmeId = :id";
            Query<Programme> query = session.createQuery(hql, Programme.class);
            query.setParameter("id", id);
            course = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return course;
    }
}
