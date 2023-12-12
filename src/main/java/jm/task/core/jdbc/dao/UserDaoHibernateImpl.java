package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoHibernateImpl implements UserDao {
    org.hibernate.cfg.Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();


    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT, `name` VARCHAR(65) NOT NULL, lastName VARCHAR(65) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))";

            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Упс...что-то пошло не так...");
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            String deleteTableSQL = "DROP TABLE IF EXISTS users";

            session.createSQLQuery(deleteTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            String deleteTableSQL = "TRUNCATE TABLE users";

            session.createSQLQuery(deleteTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
