package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.data.repository.Stat;
import org.example.data.repository.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IstatistikController {
    @FXML
    private Button alanlariGoster;
    @FXML
    private Button talepleriGoster;
    @FXML
    private Button kullanicilar;
    @FXML
    private TableView<Stat> tableView;
    @FXML
    private TableColumn<Stat, String> userColumn;
    @FXML
    private TableColumn<Stat, Integer> sayiColumn;
    @FXML
    private Button talepGoster;
    @FXML
    private Button kiraGoster;

    // TextField for getting the parameter 'n' from the user
    @FXML
    private TextField nValueField;

    @FXML
    private void initialize() {

        userColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        sayiColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        alanlariGoster.setOnAction(event -> alanlariGosterClick());
        talepleriGoster.setOnAction(event -> talepleriGosterClick());
        kullanicilar.setOnAction(event -> kullanicilarClick());
        talepGoster.setOnAction(event -> talepGosterClick());
        kiraGoster.setOnAction(event -> kiraGosterClick());
    }

    private void talepGosterClick() {
        try {
            int n = Integer.parseInt(nValueField.getText());  // Get the value from the TextField
            List<Stat> stats = getIstatistikTalep(n); // Pass the value to the PL/pgSQL function
            displayStats(stats);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle invalid input (e.g., show an alert to the user)
        }
    }

    private void kiraGosterClick() {
        try {
            int n = Integer.parseInt(nValueField.getText());  // Get the value from the TextField
            List<Stat> stats = getIstatistikRent(n); // Pass the value to the PL/pgSQL function
            displayStats(stats);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle invalid input (e.g., show an alert to the user)
        }
    }

    private List<Stat> getIstatistikRent(int n) {
        List<Stat> result = new ArrayList<>();
        String sql = "SELECT * FROM istatistik_rent(?)";  // fonksiyon çağrısı şeklinde SQL sorgusu

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Parametreyi ayarla
            stmt.setInt(1, n);
            System.out.println("SQL Sorgusu Çalıştırılıyor: " + sql);  // Debug: Sorgu yazdırılıyor

            // Sorguyu çalıştır
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Sorgu Sonuçları Alındı");  // Debug: Sonuç alındı

                // Sonuçları işle
                while (rs.next()) {
                    String fullName = rs.getString("full_name");
                    int total = rs.getInt("total");

                    // Debug: Her kayıt için alınan veriyi yazdır
                    System.out.println("full_name: " + fullName + ", total: " + total);

                    // Null kontrolü yapalım
                    if (fullName == null) {
                        System.out.println("full_name is null");
                    }
                    if (rs.wasNull()) {
                        System.out.println("total is null");
                    }

                    // Sonuçları listeye ekle
                    result.add(new Stat(fullName, total));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Toplam Sonuç Sayısı: " + result.size());  // Debug: Sonuç sayısını yazdır
        return result;
    }

    private List<Stat> getIstatistikTalep(int n) {
        List<Stat> result = new ArrayList<>();
        String sql = "SELECT * FROM istatistik_talep(?)";  // fonksiyon çağrısı şeklinde SQL sorgusu

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Parametreyi ayarla
            stmt.setInt(1, n);
            System.out.println("SQL Sorgusu Çalıştırılıyor: " + sql);  // Debug: Sorgu yazdırılıyor

            // Sorguyu çalıştır
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Sorgu Sonuçları Alındı");  // Debug: Sonuç alındı

                // Sonuçları işle
                while (rs.next()) {
                    String fullName = rs.getString("full_name");
                    int total = rs.getInt("total");

                    // Debug: Her kayıt için alınan veriyi yazdır
                    System.out.println("full_name: " + fullName + ", total: " + total);

                    // Null kontrolü yapalım
                    if (fullName == null) {
                        System.out.println("full_name is null");
                    }
                    if (rs.wasNull()) {
                        System.out.println("total is null");
                    }

                    // Sonuçları listeye ekle
                    result.add(new Stat(fullName, total));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Toplam Sonuç Sayısı: " + result.size());  // Debug: Sonuç sayısını yazdır
        return result;
    }





    // Veritabanından doğrudan SQL ile "istatistik_talep" fonksiyonunun sonucunu almak
    /*private List<Stat> getIstatistikTalep(int n) {
        List<Stat> result = new ArrayList<>();
        String sql = "SELECT * FROM istatistik_talep(?)";  // fonksiyon çağrısı şeklinde SQL sorgusu

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Parametreyi ayarla
            stmt.setInt(1, n);

            // Sorguyu çalıştır
            try (ResultSet rs = stmt.executeQuery()) {
                // Sonuçları işle
                while (rs.next()) {
                    String fullName = rs.getString("full_name");
                    int total = rs.getInt("total");

                    // Sonuçları listeye ekle
                    result.add(new Stat(fullName, total));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    private void displayStats(List<Stat> stats) {
        tableView.getItems().clear();
        tableView.getItems().addAll(stats);
    }*/

    public void displayStats(List<Stat> stats) {
        ObservableList<Stat> observableStats = FXCollections.observableArrayList(stats);
        tableView.setItems(observableStats);
    }


    @FXML
    private void alanlariGosterClick() {
        Stage currenStage = (Stage) alanlariGoster.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alanlariGoster.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("Alanlarım");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void talepleriGosterClick() {
        Stage currenStage = (Stage) talepleriGoster.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/talepleriGoster.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("Alanlarım");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void kullanicilarClick() {
        Stage currenStage = (Stage) kullanicilar.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kullanicilar.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("Kullanıcılar");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
