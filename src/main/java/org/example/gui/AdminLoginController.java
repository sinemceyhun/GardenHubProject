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
import org.example.data.entity.Admin;
import org.example.data.repository.AdminRepo;
import org.example.data.repository.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private AdminRepo adminRepo = new AdminRepo(DBConnection.getConnection());

    public AdminLoginController() throws SQLException {
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> validateLogin());
    }

    @FXML
    private void validateLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Admin admin = adminRepo.getAdminByUsername(username);  // username üzerinden sorgu

        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful!");

            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminSecondPage.fxml"));
                Parent root = loader.load();

                Stage dashboardStage = new Stage();
                dashboardStage.setTitle("Admin Giriş");
                dashboardStage.setScene(new Scene(root));
                dashboardStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
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

