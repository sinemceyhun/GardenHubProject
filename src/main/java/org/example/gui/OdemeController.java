package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.data.entity.UserField;
import org.example.data.repository.ReservationsRepo;

import java.io.IOException;


public class OdemeController {
    @FXML
    private Button alanKirala;
    @FXML
    private Button talep;
    @FXML
    private Button alanlar;
    @FXML
    private Button guncelleme;
    @FXML
    private Button odemeYap;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expireDateField;
    @FXML
    private TextField cvvField;
    @FXML
    private TextField nameField;

    private UserField selectedField; // Seçilen alan bilgisi
    private ReservationsRepo reservationsRepo = new ReservationsRepo();

    public void setSelectedField(UserField field) {
        this.selectedField = field;
    }
    @FXML
    private void initialize() {
        alanKirala.setOnAction(event -> alanKiralaClick());
        talep.setOnAction(event -> talepClick());
        alanlar.setOnAction(event -> alanlarimClick());
        guncelleme.setOnAction(event -> guncellemeClick());
        odemeYap.setOnAction(event -> handlePayment());

    }

    @FXML
    private void handlePayment() {
        if (selectedField == null) {
            showAlert("Hata", "Lütfen bir alan seçiniz.", AlertType.ERROR);
            return;
        }
        if (validatePaymentDetails()) {
            System.out.println("Removing reservation for field ID: " + selectedField.getFieldId());
            boolean success= reservationsRepo.removeReservation(selectedField.getFieldId());
            if (success) {
                showAlert("Başarılı", "Ödeme işlemi başarılı. Alanınız kaldırıldı.Kiracı olma durumunuz güncellendi!", AlertType.INFORMATION);
            } else {
                showAlert("Hata", "Ödeme işlemi sırasında bir hata oluştu.", AlertType.ERROR);
            }
        }
    }


    private boolean validatePaymentDetails() {
        String cardNumber = cardNumberField.getText();
        String expireDate = expireDateField.getText();
        String name = nameField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || expireDate.isEmpty() || cvv.isEmpty() || name.isEmpty() ){
            showAlert("Hata", "Lütfen tüm kart bilgilerini doldurun.", AlertType.ERROR);
            return false;
        }

        return true;
    }
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        cardNumberField.getScene().getWindow().hide(); // Ödeme penceresini kapat
    }
    @FXML
    private void alanKiralaClick () {
        Stage currenStage = (Stage) alanKirala.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kiralama.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("Kiralama");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void alanlarimClick () {
        Stage currenStage = (Stage) alanlar.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alanlar.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("ALANLARIM");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
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
