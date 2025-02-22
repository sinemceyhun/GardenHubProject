package org.example.data.repository;
import org.example.data.entity.Field;
import org.example.data.entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.data.entity.UserField;
public class ReservationsRepo {
    public boolean addReservation(int fieldId, int userId) {
        String sql = "INSERT INTO Reservations (user_id, field_id, start_date) VALUES (?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, fieldId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
        }

        return false;
    }


    public List<UserField> getUserFields(int userId) {
        String sql = "SELECT r.field_id, f.field_name, f.size, r.start_date, f.price_per_day " +
                "FROM Reservations r " +
                "JOIN Fields f ON r.field_id = f.field_id " +
                "WHERE r.user_id = ? AND r.status='aktif'";
        List<UserField> userFields = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserField userField = new UserField(
                            rs.getInt("field_id"),
                            rs.getString("field_name"),
                            rs.getInt("size"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDouble("price_per_day")
                    );
                    userFields.add(userField);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user fields: " + e.getMessage());
        }

        return userFields;
    }
    public List<Reservation> getUserReservations(int userId) {
        String sql = "SELECT * FROM get_user_reservations(?)";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getInt("user_id"),
                            rs.getInt("field_id"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getString("status")
                    );
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user fields: " + e.getMessage());
        }

        return reservations;
    }

    public boolean removeReservation(int fieldId) {
        String sql = "UPDATE Reservations  SET end_date = CURRENT_DATE, status = 'tamamlandı' WHERE field_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fieldId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing reservation: " + e.getMessage());
        }

        return false;
    }



}












