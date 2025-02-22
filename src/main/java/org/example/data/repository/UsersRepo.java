package org.example.data.repository;

import org.example.data.entity.User;
import org.example.data.entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepo {

    public UsersRepo() {
        // Gerekirse, burada başka işlemler yapılabilir
    }

    // Kullanıcıyı username ile getirme
    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        Users user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getBoolean("is_tenant")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }

        return user;
    }

//
    // Kullanıcıyı email ve password ile doğrulama
    public boolean validateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        boolean isValid = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
        }

        return isValid;
    }

    // Yeni kullanıcı ekleme
    public boolean addUser(Users user) {
        String sql = "INSERT INTO users (firstname, lastname, password, email, phone, is_tenant) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setLong(5, user.getPhone());
            pstmt.setBoolean(6, user.getIsTenant());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }

        return false;
    }

    // Kullanıcıyı ID'ye göre silme
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    public List<Users> yeniButon() {
        String sql = "SELECT * FROM Users EXCEPT SELECT u.* FROM Reservations r JOIN Users u ON r.user_id = u.user_id";

        List<Users> userList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Sonuç setini döngüyle işle
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getBoolean("is_tenant")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }

        return userList;
    }




    public List<Users> getUsersByUserName(String username) {
        String sql = "SELECT * FROM users WHERE firstname = ?";
        List<Users> userList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getBoolean("is_tenant")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users by username: " + e.getMessage());
        }

        return userList;
    }

    public Users getUserByUserId(Integer user_id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Users user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // İlk satıra geçiş yapılıyor
                user = new Users(
                        rs.getInt("user_id"), // Burada user_id doğrudan tablodan alınabilir
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getBoolean("is_tenant")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user by user_id: " + e.getMessage());
        }

        return user; // Kullanıcı bulunamazsa null döner
    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE users " +
                "SET firstname = ?, lastname = ?, password = ?, email = ?, phone = ? " +
                "WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());

            if(user.getPhone() != null) {
                pstmt.setLong(5, user.getPhone());
            } else {
                pstmt.setNull(5, java.sql.Types.BIGINT);
            }

            pstmt.setInt(6, user.getUserId());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0) {
                System.out.println("User updated successfully - UserID: " + user.getUserId());
                return true;
            } else {
                System.out.println("No user found with ID: " + user.getUserId());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}

