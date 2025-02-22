package org.example.data.repository;

import org.example.data.entity.CurrentUser;
import org.example.data.repository.DBConnection;
import org.example.data.entity.EquipmentRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestRepo {

    /**
     * Yeni bir talebi veritabanına ekler.
     * @param request Eklenecek talep.
     * @return İşlem başarılıysa true, aksi halde false.
     */
    public boolean addRequest(EquipmentRequest request) {
        String sql = "INSERT INTO equipmentrequests (request, requested_by) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, request.getRequest());
            stmt.setInt(2,request.getRequestedBy());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Veritabanındaki tüm talepleri döner.
     * @return Talep listesi.
     */
    public List<EquipmentRequest> getAllRequests() {
        List<EquipmentRequest> requests = new ArrayList<>();
        String sql = "SELECT request_id, request, requested_by FROM equipmentrequests";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EquipmentRequest request = new EquipmentRequest(
                        rs.getInt("request_id"),
                        rs.getString("request"),
                        rs.getInt("requested_by")
                );
                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
    public boolean deleteRequest(int requestId) {
        String sql = "DELETE FROM equipmentrequests WHERE request_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
