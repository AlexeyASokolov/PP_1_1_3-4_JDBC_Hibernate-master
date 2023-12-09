package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoHibernateImpl implements UserDao {
    org.hibernate.cfg.Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();

    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {
        session.beginTransaction();


        String createTableSQL = "CREATE TABLE users ("
                + "id BIGINT() PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,"
                + "name VARCHAR() NOT NULL," + "lastName VARCHAR() NOT NULL," + "age INT() NOT NULL)";

        session.createSQLQuery(createTableSQL).executeUpdate();
        session.getTransaction().commit();


    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();

        String createTableSQL = "TRUNCATE users";

        session.createSQLQuery(createTableSQL).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();


    }

    @Override
    public void removeUserById(long id) {

        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAllUsers() {
        session.beginTransaction();
        List<User> users = session.createCriteria(User.class).list();
        session.getTransaction().commit();
        return users;
    }


    @Override
    public void cleanUsersTable() {

        session.beginTransaction();
        User user = new User();
        while (user.getId() > 0) {
            session.delete(user);
            session.getTransaction().commit();
        }

    }
}
