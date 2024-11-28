package lk.ijse.dao.custom.impl;


import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT userId FROM User ORDER BY userId DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public List<User> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM User");
        nativeQuery.addEntity(User.class);
        List<User> users = nativeQuery.list();


        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public boolean update(User user) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user = new User();
        user.setUserId(id);
        session.remove(user);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean valid(User user) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User current_user = session.get(User.class, user.getUserId());
        if (current_user.getPassword().equals(user.getPassword())) {
            return true;
        }

        transaction.commit();
        session.close();

        return false;
    }
}
