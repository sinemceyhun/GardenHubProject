package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.data.entity.CurrentUser;
import org.example.data.entity.EquipmentRequest;
import org.example.data.repository.RequestRepo;


import java.io.IOException;

public class TalepController {
    @FXML
    private Button alanKirala;
    @FXML
    private Button alanlarim;
    @FXML
    private Button talepOlustur;
    @FXML
    private TextField talepField;
    @FXML
    private CheckBox kazma;
    @FXML
    private CheckBox kurek;
    @FXML
    private CheckBox capa;
    @FXML
    private CheckBox hortum;
    @FXML
    private CheckBox traktor;
    @FXML
    private CheckBox tohum;
    @FXML
    private CheckBox gubre;
    @FXML
    private CheckBox sulama;
    @FXML
    private CheckBox elArabasi;
    @FXML
    private CheckBox tohum2;
    private RequestRepo requestRepo= new RequestRepo();
    @FXML
    private Button guncelleme;
    @FXML
    private void initialize() {

        alanKirala.setOnAction(event -> alanKiralaClick());
        alanlarim.setOnAction(event -> alanlarimClick());
        talepOlustur.setOnAction(event -> addTalep());
        guncelleme.setOnAction(event -> guncellemeClick());
    }

    public void addTalep() {
        StringBuilder talep = new StringBuilder("");

        if (kazma.isSelected()) talep.append(" Kazma,");
        if (kurek.isSelected()) talep.append(" Kürek,");
        if (capa.isSelected()) talep.append(" Çapa,");
        if (hortum.isSelected()) talep.append(" Hortum,");
        if (traktor.isSelected()) talep.append(" Traktör,");
        if (tohum.isSelected()) talep.append(" Tohum,");
        if (gubre.isSelected()) talep.append(" Gübre,");
        if (sulama.isSelected()) talep.append(" Sulama Sistemi,");
        if (elArabasi.isSelected()) talep.append(" El Arabası,");
        if (tohum2.isSelected()) talep.append(" Fidan");

        if (talep.toString().isEmpty()) {
            showAlert("No equipment selected. Please choose at least one item.", "Error", Alert.AlertType.ERROR);
            return;
        }
        Integer requested_by = CurrentUser.getInstance().getLoggedInUser().getUserId();
        EquipmentRequest request = new EquipmentRequest(null, talep.toString(), requested_by);
        try {
            if (requestRepo.addRequest(request)){
                showAlert("TALEP İLETİLDİ", "INFORMATION", Alert.AlertType.INFORMATION);
                System.out.println("Request added.");
            }else {
                showAlert("TALEP İLETİLMESİ İÇİN KİRACI OLMANIZ GEREKLİ", "HATA", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Beklenmeyen bir hata oluştu: " + e.getMessage(),
                    "Error", Alert.AlertType.ERROR);
        }
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
            Stage currenStage = (Stage) alanlarim.getScene().getWindow();
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
        private void showAlert (String message, String title, Alert.AlertType alertType){
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.setHeaderText(null);
            alert.showAndWait();
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
