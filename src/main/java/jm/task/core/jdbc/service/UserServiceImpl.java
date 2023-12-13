package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDaoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name,lastName,age);
        System.out.printf("User с именем – %s добавлен в базу данных \n", name);
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        for (int i = 0; i < userDaoHibernate.getAllUsers().size() ; i++) {
            System.out.println(userDaoHibernate.getAllUsers().get(i));
        }
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }
}
