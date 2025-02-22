package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.data.entity.CurrentUser;
import org.example.data.entity.Field;
import org.example.data.repository.FieldsRepo;
import org.example.data.repository.ReservationsRepo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.data.entity.Users;


public class KiralamaController {


    @FXML
    private TableView<Field> availableFieldsTable;
    @FXML
    private TableColumn<Field, Integer> fieldIdColumn;
    @FXML
    private TableColumn<Field, String> fieldNameColumn;
    @FXML
    private TableColumn<Field, Integer> fieldSizeColumn;
    @FXML
    private TableColumn<Field, Double> fieldPriceColumn;



    @FXML
    private Button seciliAlaniKirala;
    @FXML
    private Button talep;
    @FXML
    private Button alanlar;
    @FXML
    private Button guncelleme;





    private final FieldsRepo fieldsRepo = new FieldsRepo();
    private final ReservationsRepo reservationsRepo = new ReservationsRepo();

    @FXML
    private void initialize() {

        fieldIdColumn.setCellValueFactory(new PropertyValueFactory<>("fieldId"));
        fieldNameColumn.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        fieldSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        fieldPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));




        talep.setOnAction(event -> talepClick());
        alanlar.setOnAction(event -> alanlarClick());
        guncelleme.setOnAction(event -> guncellemeClick());



        loadAvailableFields();


        seciliAlaniKirala.setOnAction(event -> handleFieldRental());
    }

    private void loadAvailableFields() {
        List<Field> availableFields = fieldsRepo.getAvailableFields();
        availableFieldsTable.getItems().setAll(availableFields);
    }



    private void handleFieldRental() {
        Field selectedField = availableFieldsTable.getSelectionModel().getSelectedItem();
        if (selectedField == null) {
            System.out.println("Lütfen bir alan seçiniz.");
            return;
        }


        Integer userId = CurrentUser.getInstance().getLoggedInUser().getUserId();

        boolean success = reservationsRepo.addReservation(selectedField.getFieldId(), userId);
        if (success) {
            System.out.println("Alan başarıyla kiralandı.");
            loadAvailableFields(); // Tabloyu yenile
            showSuccessMessage(selectedField);
        } else {
            System.out.println("Alan kiralama başarısız oldu.");
        }
    }

    private void showSuccessMessage(Field selectedField) {
        // Alan bilgileri
        String fieldDetails = "Alan ID: " + selectedField.getFieldId() + "\n"
                + "Alan Adı: " + selectedField.getFieldName() + "\n"
                + "Boyut: " + selectedField.getSize() + "\n"
                + "Kira: " + selectedField.getPricePerDay() + "\n";


        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Başarılı");
        successAlert.setHeaderText("Bu alan başarıyla kiralandı!Kiracı olma durumunuz güncellendi.");
        successAlert.setContentText("Alan Bilgileri:\n" + fieldDetails);

        ButtonType goToFields = new ButtonType("Alanlarım'a Git");
        successAlert.getButtonTypes().setAll(goToFields, ButtonType.CLOSE);

        Optional<ButtonType> result = successAlert.showAndWait();
        if (result.isPresent() && result.get() == goToFields) {
            alanlarClick();
        }
    }


    @FXML
    private void talepClick() {
        Stage currenStage= (Stage) talep.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/talep.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("Talep");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void guncellemeClick() {
        Stage currenStage= (Stage) talep.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUser.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("guncelleme");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void alanlarClick() {
        Stage currenStage= (Stage) alanlar.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alanlar.fxml"));
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
