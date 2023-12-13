package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn = Util.getConnection();


    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(64) NOT NULL,  
                    lastName VARCHAR(64) NOT NULL, age INT NOT NULL)""";
        try (PreparedStatement statement = conn.prepareStatement(createTableSQL)) {
            statement.executeUpdate(createTableSQL);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }


    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (PreparedStatement statement = conn.prepareStatement(dropTableSQL);) {
            statement.executeUpdate(dropTableSQL);
        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(saveUserSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String saveUserSQL = "DELETE FROM users WHERE id= ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(saveUserSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "SELECT id, name, lastName, age FROM `users`";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectAllUsersSQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String selectAllUsersSQL = "TRUNCATE TABLE users";
        try (PreparedStatement statement = conn.prepareStatement(selectAllUsersSQL)) {
            statement.executeUpdate(selectAllUsersSQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
