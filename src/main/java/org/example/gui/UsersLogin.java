package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.data.entity.CurrentUser;
import org.example.data.entity.Users;
import org.example.data.repository.UsersRepo;

import java.io.IOException;
import java.net.URL;

public class UsersLogin {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private UsersRepo usersRepo = new UsersRepo();

    @FXML
    private void initialize() {
        // Login butonuna tıklama olayını ayarla
        loginButton.setOnAction(event -> validateLogin());
    }

    @FXML
    private void validateLogin() {
        String username = usernameField.getText(); // Kullanıcı adını al
        String password = passwordField.getText(); // Şifreyi al

        // Kullanıcıyı UsersRepo üzerinden al
        Users user = usersRepo.getUserByEmail(username);

        if (user != null && usersRepo.validateUser(user.getEmail(), password)) {
            System.out.println("Login successful!");
            // Kullanıcıyı sakla
            CurrentUser.getInstance().setLoggedInUser(user);
            // Mevcut sahneyi kapat
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

            try {
                // Yeni sahneyi yükle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/userSecondPage.fxml"));
                Parent root = loader.load();

                // Yeni sahneyi göster
                Stage secondStage = new Stage();
                secondStage.setTitle("Second Page");
                secondStage.setScene(new Scene(root));
                secondStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Kullanıcı adı veya şifre hatalıysa hata mesajı göster
            showAlert("Invalid username or password.", "Login Failed", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
