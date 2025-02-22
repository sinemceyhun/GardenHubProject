package org.example.data.repository;

import org.example.data.entity.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepo {
    private final Connection connection;

    public AdminRepo(Connection connection) {
        this.connection = connection;
    }

    // Add a new admin
    public int getNextAdminId() throws SQLException {
        String query = "SELECT nextval('admin_id_seq')";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Failed to fetch next sequence value.");
            }
        }
    }

    public boolean saveAdmin(Admin admin) {
        String query = "INSERT INTO admins (admin_id, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, admin.getAdminId());
            preparedStatement.setString(2, admin.getUsername());
            preparedStatement.setString(3, admin.getPassword());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
            return false;
        }
    }

    // Retrieve an admin by ID
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM admins WHERE admin_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToAdmin(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin by ID: " + e.getMessage());
        }
        return null;
    }

    // Retrieve all admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admins";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                admins.add(mapResultSetToAdmin(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all admins: " + e.getMessage());
        }
        return admins;
    }

    // Update an admin
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admins SET username = ?, password = ? WHERE admin_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setInt(3, admin.getAdminId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            return false;
        }
    }

    // Delete an admin by ID
    public boolean deleteAdminById(int adminId) {
        String query = "DELETE FROM admins WHERE admin_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, adminId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            return false;
        }
    }

    // Helper method to map a ResultSet row to an Admin object
    private Admin mapResultSetToAdmin(ResultSet resultSet) throws SQLException {
        return new Admin(
                resultSet.getInt("admin_id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

    public Admin getAdminByUsername(String username) {
        String query = "SELECT * FROM admins WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Admin(
                        resultSet.getInt("admin_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin by username: " + e.getMessage());
        }
        return null;
    }
}