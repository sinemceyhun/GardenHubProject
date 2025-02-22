package org.example.data.repository;
import java.util.List;
import java.util.ArrayList;
import org.example.data.entity.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldsRepo {
    public List<Field> getAvailableFields() {
        String sql = "SELECT field_id, field_name, size,price_per_day FROM AvailableFields";
        List<Field> fields = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Field field = new Field(
                        rs.getInt("field_id"),
                        rs.getString("field_name"),
                        rs.getInt("size"),
                        //   rs.getString("status"),
                        rs.getDouble("price_per_day")
                        //  rs.getObject("rented_by", Integer.class)

                );
                fields.add(field);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching available fields: " + e.getMessage());
        }

        return fields;
    }
}


