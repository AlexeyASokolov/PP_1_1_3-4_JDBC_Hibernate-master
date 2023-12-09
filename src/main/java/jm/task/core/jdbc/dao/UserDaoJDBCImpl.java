package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Column;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE users ("
                + "id BIGINT() PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,"
                + "name VARCHAR() NOT NULL," + "lastName VARCHAR() NOT NULL," + "age INT() NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        } catch (Exception ex) {
            System.out.println("Сбой соединения с базой данных");
            System.out.println(ex);
        }

    }



    public void dropUsersTable() {
        String dropTableSQL = "TRUNCATE users";
        try (Connection conn = Util.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(dropTableSQL);
        } catch (Exception ex) {
            System.out.println("Сбой соединения с базой данных");
            System.out.println(ex);
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(saveUserSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("Пользователь успешно добавлен!");

        } catch (SQLException ex) {
            System.out.println("Сбой соединения с базой данных");
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String saveUserSQL = "delete from users where id= ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(saveUserSQL)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Пользователь удален.");

        } catch (SQLException ex) {
            System.out.println("Сбой соединения с базой данных.");
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM users";

        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                userList.add(user);
            }

        } catch (SQLException ex) {
            System.out.println("Сбой соединения с базой данных.");
            ex.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "DELETE FROM users";

        try (Connection conn = Util.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(selectAllUsersSQL);
        } catch (SQLException ex) {
            System.out.println("Сбой соединения с базой данных.");
            ex.printStackTrace();
        }
    }
}
