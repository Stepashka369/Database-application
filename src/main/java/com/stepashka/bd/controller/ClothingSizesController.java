package com.stepashka.bd.controller;

import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.entity.ClothingSize;
import com.stepashka.bd.storage.ClothingSizeStorageImpl;
import com.stepashka.bd.storage.ObjectStorage;
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

public class ClothingSizesController extends AbstractController implements Initializable {

    @FXML
    private TableColumn<ClothingSize, String> codeColumn;
    @FXML
    private TextField codeTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button insertButton;
    @FXML
    private TableColumn<ClothingSize, String> noteColumn;
    @FXML
    private TextField noteTextField;
    @FXML
    private TableView<ClothingSize> tableView;
    @FXML
    private Button updateButton;

    private ObjectStorage clothingSizeStorage;
    private ObservableList<ClothingSize> observableList;
    private ClothingSize selectedItem;

    @FXML
    void onDeleteButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                clothingSizeStorage.delete(selectedItem.getId());
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
            var item = ClothingSize.builder()
                    .code(codeTextField.getText())
                    .note(noteTextField.getText())
                    .build();
            item.setId(clothingSizeStorage.save(item));
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
                        .code(codeTextField.getText())
                        .note(noteTextField.getText())
                        .build();
                observableList.remove(selectedItem);
                clothingSizeStorage.update(item);
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
            clothingSizeStorage = new ClothingSizeStorageImpl();
            observableList = FXCollections.observableList(clothingSizeStorage.getAll());
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
            tableView.setItems(observableList);
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedItem = newValue;
                    codeTextField.setText(newValue.getCode());
                    noteTextField.setText(newValue.getNote());
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
