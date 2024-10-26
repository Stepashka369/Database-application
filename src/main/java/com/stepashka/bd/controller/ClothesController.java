//package com.stepashka.bd.controller;
//
//import com.stepashka.bd.error.StorageException;
//import com.stepashka.bd.model.Clients;
//import com.stepashka.bd.model.ClothingBrands;
//import com.stepashka.bd.storage.ClothingSizeStorageImpl;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class ClothesController implements Initializable {
//
//    @FXML
//    private TableColumn<ClothingBrands, String> brandColumn;
//    @FXML
//    private ComboBox<String> brandComboBox;
//    @FXML
//    private TableColumn<Clients, String> costColumn;
//    @FXML
//    private Button deleteButton;
//    @FXML
//    private Button insertButton;
//    @FXML
//    private TableColumn<Clients, String> noteColumn;
//    @FXML
//    private TextField noteTextField;
//    @FXML
//    private TableColumn<?, ?> sizeColumn;
//    @FXML
//    private ComboBox<?> sizeComboBox;
//    @FXML
//    private TextField surnameTextField;
//    @FXML
//    private TableView<?> tableView;
//    @FXML
//    private TableColumn<?, ?> typeColumn;
//    @FXML
//    private ComboBox<?> typeComboBox;
//    @FXML
//    private Button updateButton;
//
//    @FXML
//    void onDeleteButton(ActionEvent event) {
//
//    }
//
//    @FXML
//    void onInsertButton(ActionEvent event) {
//
//    }
//
//    @FXML
//    void onUpdateButton(ActionEvent event) {
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            clothingSizeStorage = new ClothingSizeStorageImpl();
//            observableList = FXCollections.observableList(clothingSizeStorage.getAll());
//            codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
//            noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
//            tableView.setItems(observableList);
//            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//                if (newValue != null) {
//                    selectedItem = newValue;
//                    codeTextField.setText(newValue.getCode());
//                    noteTextField.setText(newValue.getNote());
//                }
//            });
//        } catch (StorageException ex) {
//            showErrorAlert("Ошибка при инициализации окна.");
//        }
//    }
//
//    private void showErrorAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("ERROR");
//        alert.setHeaderText(message);
//        alert.showAndWait();
//    }
//
//    private void showInformationAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("INFORMATION");
//        alert.setHeaderText(message);
//        alert.showAndWait();
//    }
//
//    private boolean isSelectedItemAndId() {
//        return (selectedItem != null && selectedItem.getId() != null);
//    }
//}
