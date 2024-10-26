package com.stepashka.bd.controller;

import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.model.Clients;
import com.stepashka.bd.storage.ClientStorageImpl;
import com.stepashka.bd.storage.ObjectStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Clients, String> emailColumn;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button insertButton;
    @FXML
    private TableColumn<Clients, String> nameColumn;
    @FXML
    private TextField nameTextField;
    @FXML
    private TableColumn<?, ?> surnameColumn;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TableView<Clients> tableView;
    @FXML
    private Button updateButton;

    private ObjectStorage clientsStorage;
    private ObservableList<Clients> observableList;
    private Clients selectedItem;

    @FXML
    void onDeleteButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                clientsStorage.delete(selectedItem.getId());
                observableList.remove(selectedItem);
                selectedItem = null;
            } else {
                showInformationAlert("Объект таблицы не выбран.");
            }

        } catch (StorageException ex){
            showErrorAlert("Ошибка при удалении объекта.");
        }
    }

    @FXML
    void onInsertButton(ActionEvent event) {
        try{
            var item = Clients.builder()
                    .name(nameTextField.getText())
                    .surname(surnameTextField.getText())
                    .email(emailTextField.getText())
                    .build();
            observableList.add(item);
            clientsStorage.save(item);
        } catch (StorageException ex){
            showErrorAlert("Ошибка при добавлении объекта.");
        }
    }

    @FXML
    void onUpdateButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                var item = selectedItem.toBuilder()
                        .name(nameTextField.getText())
                        .surname(surnameTextField.getText())
                        .email(emailTextField.getText())
                        .build();
                observableList.remove(selectedItem);
                clientsStorage.update(item);
                observableList.add(item);
                selectedItem = null;
            } else {
                showInformationAlert("Объект таблицы не выбран.");
            }
        } catch (StorageException ex){
            showErrorAlert("Ошибка при редактировании объекта.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clientsStorage = new ClientStorageImpl();
            observableList = FXCollections.observableList(clientsStorage.getAll());
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableView.setItems(observableList);
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedItem = newValue;
                    nameTextField.setText(newValue.getName());
                    surnameTextField.setText(newValue.getSurname());
                    emailTextField.setText(newValue.getEmail());
                }
            });
        } catch (StorageException ex) {
            showErrorAlert("Ошибка при инициализации окна.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private boolean isSelectedItemAndId() {
        return (selectedItem != null && selectedItem.getId() != null);
    }
}
