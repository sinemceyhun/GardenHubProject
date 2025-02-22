package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.data.entity.*;
import org.example.data.repository.ReservationsRepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class AlanlarController {
    @FXML
    private Button alanKirala;
    @FXML
    private Button talep;
    @FXML
    private Button odemeYap;
    @FXML
    private Button guncelleme;


    @FXML
    private TableView<UserField> userFieldsTable;
    @FXML
    private TableColumn<UserField, Integer> fieldIdColumn;
    @FXML
    private TableColumn<UserField, String> fieldNameColumn;
    @FXML
    private TableColumn<UserField, Integer> fieldSizeColumn;
    @FXML
    private TableColumn<UserField, Date> startDateColumn;
    @FXML
    private TableColumn<UserField, Double> fieldPriceColumn;

    @FXML
    private Label fiyat;
    private final ReservationsRepo reservationsRepo = new ReservationsRepo();

    @FXML
    private void initialize() {

        alanKirala.setOnAction(event -> alanKiralaClick());
        talep.setOnAction(event -> talepClick());
        odemeYap.setOnAction(event -> odemeYapClick());
        guncelleme.setOnAction(event -> guncellemeClick());
        userFieldsTable.setOnMouseClicked(event -> showPayment());

       fieldIdColumn.setCellValueFactory(new PropertyValueFactory<>("fieldId"));
       fieldNameColumn.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
       fieldSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
       startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
       fieldPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

       loadUserFields();

    }
    private void showPayment() {
        UserField selectedField = userFieldsTable.getSelectionModel().getSelectedItem();
        if (selectedField != null) {
            double payment = calculatePayment(selectedField.getStartDate(), selectedField.getPricePerDay());
            fiyat.setText("" + payment + "");
        } else {
            fiyat.setText(" ");
        }
    }

    private double calculatePayment(LocalDate startDate, double pricePerDay) {
        LocalDate today = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startDate, today);
        if(totalDays==0) {
            return pricePerDay;
        }
        else {
            return (totalDays+1) * pricePerDay;
        }
    }

    private void loadUserFields() {
        Integer userId = CurrentUser.getInstance().getLoggedInUser().getUserId();
        List<UserField> userFields = reservationsRepo.getUserFields(userId);
        userFieldsTable.getItems().setAll(userFields);
    }




    @FXML
    private  void alanKiralaClick(){
        Stage currenStage= (Stage) alanKirala.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kiralama.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Kiralama");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void talepClick(){
        Stage currenStage= (Stage) talep.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/talep.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Alanlarım");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void odemeYapClick(){

        UserField selectedField = userFieldsTable.getSelectionModel().getSelectedItem();
        if (selectedField == null) {
            System.out.println("Lütfen bir alan seçiniz.");
            return;
        }
        Stage currenStage= (Stage) odemeYap.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/odeme.fxml"));
            Parent root= loader.load();

           OdemeController odemeController = loader.getController();
            odemeController.setSelectedField(selectedField);

            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Ödeme");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void guncellemeClick(){
        Stage currenStage= (Stage) guncelleme.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUser.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Alanlarım");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
