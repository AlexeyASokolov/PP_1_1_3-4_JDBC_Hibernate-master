package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("First", "Firsrov", ( byte) 1);
        userService.saveUser("Second", "Secondov", ( byte) 2);
        userService.saveUser("Pupa", "Lupov", ( byte) 69);
        userService.saveUser("Lupa", "Pupov", ( byte) 96);
        userService.removeUserById(3);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
