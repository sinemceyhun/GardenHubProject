package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSecondPageController {
    @FXML
    private Button alanlariGoster;
    @FXML
    private Button talepleriGoster;
    @FXML
    private Button istatistik;
    @FXML
    private Button kullanicilar;
    @FXML
    private void initialize() {

        alanlariGoster.setOnAction(event -> alanlariGosterClick());
        talepleriGoster.setOnAction(event -> talepleriGosterClick());
        istatistik.setOnAction(event -> istatistikClick());
        kullanicilar.setOnAction(event -> kullanicilarClick());
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
