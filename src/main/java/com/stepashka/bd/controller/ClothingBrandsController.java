package com.stepashka.bd.controller;

import com.stepashka.bd.entity.ClothingBrands;
import com.stepashka.bd.entity.ClothingSize;
import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.storage.ClothingBrandStorageImpl;
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

public class ClothingBrandsController extends AbstractController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private TableColumn<ClothingBrands, String> codeColumn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TableColumn<ClothingSize, String> noteColumn;

    @FXML
    private TextField noteTextField;

    @FXML
    private TableView<ClothingBrands> tableView;

    @FXML
    private Button updateButton;

    private ObjectStorage clothingBrandStorage;
    private ObservableList<ClothingBrands> observableList;
    private ClothingBrands selectedItem;

    @FXML
    void onDeleteButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                clothingBrandStorage.delete(selectedItem.getId());
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
            var item = ClothingBrands.builder()
                    .code(codeTextField.getText())
                    .note(noteTextField.getText())
                    .build();
            item.setId(clothingBrandStorage.save(item));
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
                clothingBrandStorage.update(item);
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
            clothingBrandStorage = new ClothingBrandStorageImpl();
            observableList = FXCollections.observableList(clothingBrandStorage.getAll());
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

    private void clearTextFields(){
        codeTextField.setText("");
        noteTextField.setText("");
    }

    private boolean isSelectedItemAndId() {
        return (selectedItem != null && selectedItem.getId() != null);
    }
}
