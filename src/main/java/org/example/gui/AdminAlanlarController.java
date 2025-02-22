package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.data.entity.CurrentUser;
import org.example.data.entity.Reservation;
import org.example.data.entity.UserField;
import org.example.data.repository.ReservationsRepo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AdminAlanlarController {
    @FXML
    private Button kullanicilar;
    @FXML
    private Button talepleriGoster;
    @FXML
    private Button istatistik;
    @FXML
    private Button kullaniciGoster;
    @FXML
    private TextField userIdInput;
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, Integer> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> userIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> fieldIdColumn;
    @FXML
    private TableColumn<Reservation, Date> startDateColumn;
    @FXML
    private TableColumn<Reservation, Date> endDateColumn;
    @FXML
    private TableColumn<Reservation, String> statusColumn;


    private final ReservationsRepo reservationsRepo = new ReservationsRepo();

  @FXML
    private void initialize() {

        kullanicilar.setOnAction(event -> kullanicilarClick());
        talepleriGoster.setOnAction(event -> talepleriGosterClick());
        istatistik.setOnAction(event -> istatistikClick());
        kullaniciGoster.setOnAction(event -> showFields());

        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        fieldIdColumn.setCellValueFactory(new PropertyValueFactory<>("fieldId"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    @FXML
    private  void kullanicilarClick(){
        Stage currenStage= (Stage) kullanicilar.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kullanicilar.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Kullanıcılar");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private  void showFields(){
            String userIdText=userIdInput.getText();
        if (userIdText.isEmpty()) {
            showAlert("Hata", "Lütfen bir kullanıcı ID'si girin.", Alert.AlertType.ERROR);
            return;
        }
        try {
            int userId = Integer.parseInt(userIdText);
            List<Reservation> reservations = reservationsRepo.getUserReservations(userId);
            reservationsTable.getItems().setAll(reservations);
        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçerli bir kullanıcı ID'si girin.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private  void talepleriGosterClick(){
        Stage currenStage= (Stage) talepleriGoster.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/talepleriGoster.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("TALEPLER");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private  void istatistikClick(){
        Stage currenStage= (Stage) istatistik.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/istatistik.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("İstatistik");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
