package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(15) NOT NULL , " +
                "lastName VARCHAR(25) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try(Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?);";

        try(Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?;";

        try(Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users;";
        try (Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.printf(String.valueOf(statement.executeQuery()));
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> usersList = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                usersList.add(user);
            }
            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";
        try(Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
