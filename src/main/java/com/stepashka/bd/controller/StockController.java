package com.stepashka.bd.controller;

import com.stepashka.bd.entity.Stock;
import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.storage.ObjectStorage;
import com.stepashka.bd.storage.StockStorageImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController extends AbstractController implements Initializable {

    @FXML
    private TableColumn<Stock, String> addressColumn;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private TableView<Stock> tableView;

    @FXML
    private TableColumn<Stock, String> townColumn;

    @FXML
    private TextField townTextField;

    @FXML
    private Button updateButton;

    private ObjectStorage stockStorage;
    private ObservableList<Stock> observableList;
    private Stock selectedItem;

    @FXML
    void onDeleteButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                stockStorage.delete(selectedItem.getId());
                observableList.remove(selectedItem);
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
            var item = Stock.builder()
                    .town(townTextField.getText())
                    .address(addressTextField.getText())
                    .build();
            item.setId(stockStorage.save(item));
            observableList.add(item);
        } catch (StorageException ex){
            showErrorAlert("Ошибка при добавлении объекта.");
        }
    }

    @FXML
    void onUpdateButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                var item = selectedItem.toBuilder()
                        .town(townTextField.getText())
                        .address(addressTextField.getText())
                        .build();
                observableList.remove(selectedItem);
                stockStorage.update(item);
                observableList.add(item);
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
            stockStorage = new StockStorageImpl();
            observableList = FXCollections.observableList(stockStorage.getAll());
            townColumn.setCellValueFactory(new PropertyValueFactory<>("town"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            tableView.setItems(observableList);
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedItem = newValue;
                    townTextField.setText(newValue.getTown());
                    addressTextField.setText(newValue.getAddress());
                }
            });
        } catch (StorageException ex) {
            showErrorAlert("Ошибка при инициализации окна.");
        }
    }

    private boolean isSelectedItemAndId() {
        return (selectedItem != null && selectedItem.getId() != null);
    }
}
