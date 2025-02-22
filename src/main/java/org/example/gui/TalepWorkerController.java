package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.data.entity.EquipmentRequest;
import org.example.data.repository.RequestRepo;

import java.io.IOException;

public class TalepWorkerController {
    @FXML
    private Button alanlariGoster;
    @FXML
    private Button kullanicilar;
    @FXML
    private Button istatistik;
    @FXML
    private Button delete;
    private RequestRepo requestRepo=new RequestRepo();

    @FXML
    private TableView<EquipmentRequest> tableView;

    @FXML
    private TableColumn<EquipmentRequest, Integer> idColumn;

    @FXML
    private TableColumn<EquipmentRequest, String> requestColumn;

    @FXML
    private TableColumn<EquipmentRequest, String> userColumn;

    private final ObservableList<EquipmentRequest> requestList = FXCollections.observableArrayList();
    @FXML
    private void initialize() {
        setupTableColumns();

        // "ALANLARI GÖSTER" butonunun davranışı
        alanlariGoster.setOnAction(event -> alanlariGosterClick());
        kullanicilar.setOnAction(event -> kullanicilarClick());
        istatistik.setOnAction(event -> istatistikClick());


        // "SEÇİLİ SATIRI SİL" butonunun davranışı
        delete.setOnAction(event -> deleteSelectedRow());

        // Verileri tabloya yükle
        loadRequests();
    }
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        requestColumn.setCellValueFactory(new PropertyValueFactory<>("request"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
    }

    /**
     * Tüm talepleri veritabanından alır ve tabloya ekler.
     */
    private void loadRequests() {
        requestList.clear();
        requestList.addAll(requestRepo.getAllRequests());
        tableView.setItems(requestList);
    }

    /**
     * Seçili satırı siler ve tabloyu günceller.
     */
    private void deleteSelectedRow() {
        EquipmentRequest selectedRequest = tableView.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            boolean isDeleted = requestRepo.deleteRequest(selectedRequest.getRequestId());
            if (isDeleted) {
                requestList.remove(selectedRequest);
            } else {
                System.err.println("Seçili satır silinemedi.");
            }
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
