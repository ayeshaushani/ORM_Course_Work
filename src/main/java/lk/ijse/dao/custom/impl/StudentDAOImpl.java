package lk.ijse.dao.custom.impl;


import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean add(Student student) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(student);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT student_id FROM Student ORDER BY student_id DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public List<Student> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Student");
        nativeQuery.addEntity(Student.class);
        List<Student> customers = nativeQuery.list();


        transaction.commit();
        session.close();

        return customers;

    }

    @Override
    public boolean update(Student student) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = new Student();
        student.setStudent_id(id);
        session.remove(student);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<Student> SearchSID(String sid) throws IOException {
        List<Student> studentModels = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentModels = session.createQuery("FROM Student WHERE  student_id = :sid", Student.class)
                    .setParameter("sid", sid)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return studentModels;
    }

    @Override
    public Student searchById(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Student student = null;

        try {
            transaction = session.beginTransaction();
            // find student
            String hql = "FROM Student WHERE student_id = :id";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("id", id);
            student = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }

    @Override
    public List<User> getid() {
        List<User> users = new ArrayList<>();
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Use HQL to get all User objects
            String hql = "FROM User"; // This will retrieve all User entities
            Query<User> query = session.createQuery(hql, User.class);
            users = query.list(); // This will now contain User objects
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger instead of printStackTrace
        }
        return users;
    }
}
