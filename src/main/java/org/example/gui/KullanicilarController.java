package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.data.entity.EquipmentRequest;
import org.example.data.entity.User;
import org.example.data.entity.Users;
import org.example.data.repository.UsersRepo;

import java.io.IOException;
import java.util.List;

public class KullanicilarController {
    @FXML
    private Button alanlariGoster;
    @FXML
    private Button talepleriGoster;
    @FXML
    private Button istatistik;
    @FXML
    private Button kullaniciGoster;
    @FXML
    private Button yeniButon;
    @FXML
    private Button delete;
    @FXML
    private TextField name;
    @FXML
    private TableView<Users> tableView;
    @FXML
    private TableColumn<Users, String> userId;
    @FXML
    private TableColumn<Users, String> isim;
    @FXML
    private TableColumn<Users, String> soyisim;
    @FXML
    private TableColumn<Users, String> email;
    @FXML
    private TableColumn<Users, String> telefon;
    @FXML
    private TableColumn<Users, String> kiraciMi;
    UsersRepo repo=new UsersRepo();
    private final ObservableList<Users> users = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        alanlariGoster.setOnAction(event -> alanlariGosterClick());
        talepleriGoster.setOnAction(event -> talepleriGosterClick());
        istatistik.setOnAction(event -> istatistikClick());
        kullaniciGoster.setOnAction(event -> kullaniciGosterClick());
        yeniButon.setOnAction(event-> yeniButonClick());
        delete.setOnAction(event-> deleteSelectedRow());
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        isim.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        soyisim.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
        kiraciMi.setCellValueFactory(new PropertyValueFactory<>("isTenant"));



    }
    @FXML
    private void deleteSelectedRow() {
        Users user = tableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            boolean isDeleted = repo.deleteUser(user.getUserId());
            if (isDeleted) {
                users.remove(user); // Listeyi güncelle
                tableView.setItems(null); // Tabloyu sıfırla
                tableView.setItems(users); // Güncellenmiş listeyi yeniden bağla
            } else {
                System.err.println("Seçili satır silinemedi.");
            }
        } else {
            showAlert("Hata", "Lütfen silmek için bir satır seçin.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void kullaniciGosterClick() {
        // Kullanıcı girdisini al ve boşluk kontrolü yap
        String userInput = name.getText();
        if (userInput == null || userInput.trim().isEmpty()) {
            showAlert("Hata", "Lütfen bir isim giriniz.", Alert.AlertType.ERROR);
            return;
        }

        // Veri tabanından kullanıcıyı getir
        List<Users> users = repo.getUsersByUserName(userInput);
        if (users != null && !users.isEmpty()) {
            // TableView'i güncelle
            tableView.getItems().clear();
            tableView.getItems().addAll(users); // Listeyi doğrudan ekle
        } else {
            showAlert("Bilgi", "Girilen isme ait kullanıcı bulunamadı.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void yeniButonClick() {

        // Veri tabanından kullanıcıyı getir
        List<Users> users = repo.yeniButon();
        if (users != null && !users.isEmpty()) {
            // TableView'i güncelle
            tableView.getItems().clear();
            tableView.getItems().addAll(users); // Listeyi doğrudan ekle
        } else {
            showAlert("Bilgi", "Hiç kiralama yapmayan kullanıcı bulunamadı.", Alert.AlertType.INFORMATION);
        }
    }


    @FXML
    private  void alanlariGosterClick(){
        Stage currenStage= (Stage) alanlariGoster.getScene().getWindow();
        currenStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alanlariGoster.fxml"));
            Parent root= loader.load();
            Stage AddGroupStage= new Stage();
            AddGroupStage.setTitle("ALANLAR");
            AddGroupStage.setScene(new Scene(root));
            AddGroupStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
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
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}