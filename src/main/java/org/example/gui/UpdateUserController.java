package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.data.entity.Users;
import org.example.data.repository.UsersRepo;
import org.example.data.entity.CurrentUser;

import java.io.IOException;

public class UpdateUserController {
    @FXML
    private Button alanKirala;
    @FXML
    private Button talep;
    @FXML
    private Button alanlar;
    @FXML
    private Button updateButton;

    // Form elemanları
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField gsmField;
    @FXML
    private PasswordField passwordField;

    private UsersRepo usersRepo = new UsersRepo();

    @FXML
    private void initialize() {
        // Mevcut buton eventleri
        alanKirala.setOnAction(event -> alanKiralaClick());
        talep.setOnAction(event -> talepClick());
        alanlar.setOnAction(event -> alanlarimClick());

        // Update butonu eventi
        updateButton.setOnAction(event -> handleUpdate());

        // Mevcut kullanıcı bilgilerini form alanlarına doldur
        loadUserData();
    }
//
    private void loadUserData() {
        Users currentUser = CurrentUser.getInstance().getLoggedInUser();
        if (currentUser != null) {
            nameField.setText(currentUser.getFirstName());
            surnameField.setText(currentUser.getLastName());
            mailField.setText(currentUser.getEmail());
            passwordField.setText(currentUser.getPassword());
            if (currentUser.getPhone() != null) {
                gsmField.setText(String.valueOf(currentUser.getPhone()));
            }
        }
    }

    private void handleUpdate() {
        try {
            // Form validasyonu
            if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() ||
                    mailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert("Lütfen tüm zorunlu alanları doldurun!", "Error", Alert.AlertType.ERROR);
                return;
            }

            Users currentUser = CurrentUser.getInstance().getLoggedInUser();
            // Yeni değerleri set et
            currentUser.setFirstName(nameField.getText());
            currentUser.setLastName(surnameField.getText());
            currentUser.setEmail(mailField.getText());
            if (passwordField.getText().length()<4) {
                showAlert("ŞİFRE 4 KARAKTERDEN AZ OLMAMALI.", "Error", Alert.AlertType.ERROR);
                return;
            }
            currentUser.setPassword(passwordField.getText());

            // GSM alanı boş değilse güncelle
            if (!gsmField.getText().isEmpty()) {
                try {
                    currentUser.setPhone(Long.parseLong(gsmField.getText()));
                } catch (NumberFormatException e) {
                    showAlert("Lütfen geçerli bir telefon numarası girin!", "Error", Alert.AlertType.ERROR);
                    return;
                }
            }
            // Veritabanında güncelle
            if (usersRepo.updateUser(currentUser)) {
                showAlert("Bilgileriniz başarıyla güncellendi!", "Error", Alert.AlertType.INFORMATION);
                // CurrentUser singleton'ını da güncelle
                CurrentUser.getInstance().setLoggedInUser(currentUser);
            } else {
                showAlert("Güncelleme sırasında bir hata oluştu!", "Error", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Beklenmeyen bir hata oluştu: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, String error, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Hata" : "Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void alanKiralaClick() {
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
    private void alanlarimClick() {
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
    private void talepClick() {
        Stage currenStage = (Stage) talep.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/talep.fxml"));
            Parent root = loader.load();
            Stage AddGroupStage = new Stage();
            AddGroupStage.setTitle("Alanlarım");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}