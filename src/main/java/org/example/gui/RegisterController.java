package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.data.entity.Users;
import org.example.data.repository.UsersRepo;

import java.io.IOException;

public class RegisterController {
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
    @FXML
    private Button registerButton;

    private UsersRepo usersRepo = new UsersRepo();

    @FXML
    private void initialize() {
        registerButton.setOnAction(event -> registerUser());
    }
    @FXML
    private void registerUser() {
        // Form alanlarındaki değerleri alıyoruz
        String firstName = nameField.getText();
        String lastName = surnameField.getText();
        String email = mailField.getText();
        String password = passwordField.getText();
        String phoneText = gsmField.getText();

        // Telefon numarası doğrulaması
        Long phone = null;
        try {
            if (!phoneText.isEmpty()) {
                phone = Long.parseLong(phoneText);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid phone number.", "Error", Alert.AlertType.ERROR);
            return;
        }

        // Zorunlu alanların doldurulduğunu kontrol ediyoruz
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("All fields must be filled.", "Error", Alert.AlertType.ERROR);
            return;
        }

        // Kullanıcı nesnesini oluşturuyoruz
        Users newUser = new Users(null, firstName, lastName, password, email, phone, false);

        // Kullanıcıyı veritabanına kaydediyoruz
        boolean isUserAdded = usersRepo.addUser(newUser);

        if (isUserAdded) {
            showAlert("User registered successfully!", "Success", Alert.AlertType.INFORMATION);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/userLogin.fxml"));
                Parent root= loader.load();
                Stage AddGroupStage= new Stage();
                AddGroupStage.setTitle("Login");
                AddGroupStage.setScene(new Scene(root));
                AddGroupStage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
            clearFields();
        } else {
            if (password.length()<4) {
                showAlert("ŞİFRE 4 KARAKTERDEN AZ OLMAMALI", "Error", Alert.AlertType.ERROR);
                return;
            }
            showAlert("Failed to register user. Please try again.", "Error", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        // Form alanlarını temizliyoruz
        nameField.clear();
        surnameField.clear();
        mailField.clear();
        gsmField.clear();
        passwordField.clear();
    }

    private void showAlert(String message, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
