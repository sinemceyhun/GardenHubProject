package org.example.data.repository;

import org.example.data.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepo {
    private final Connection connection;

    public UserRepo(Connection connection) {
        this.connection = connection;
    }

    // Add a new user

    public int getNextUserId() throws SQLException {
        String query = "SELECT nextval('user_id_seq')";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Failed to fetch next sequence value.");
            }
        }
    }
    public boolean saveUser(User user) {
        String query = "INSERT INTO Users (user_id, firstName, lastName, password, email, phone, is_tenant) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,user.getUserId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, user.getPhone());
            preparedStatement.setBoolean(7, user.isTenant());

            return preparedStatement.executeUpdate() > 0; // Return true if the user was added
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    // Retrieve a user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
        }
        return null; // Return null if user not found
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }
        return users;
    }

    // Update a user
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET firstName = ?, lastName = ?, password = ?, email = ?, phone = ?, is_tenant = ? WHERE userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setLong(5, user.getPhone());
            preparedStatement.setBoolean(6, user.isTenant());
            preparedStatement.setInt(7, user.getUserId());
            return preparedStatement.executeUpdate() > 0; // Return true if the user was updated
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete a user by ID
    public boolean deleteUserById(int userId) {
        String query = "DELETE FROM Users WHERE userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() > 0; // Return true if the user was deleted
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Helper method to map a ResultSet row to a User object
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("userId"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getLong("phone"),
                resultSet.getBoolean("is_tenant")
        );
    }
}
