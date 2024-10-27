package com.stepashka.bd.controller;

import com.stepashka.bd.entity.Clothes;
import com.stepashka.bd.entity.ClothingBrand;
import com.stepashka.bd.entity.ClothingSize;
import com.stepashka.bd.entity.ClothingType;
import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.storage.ClothesStorageImpl;
import com.stepashka.bd.storage.ClothingBrandStorageImpl;
import com.stepashka.bd.storage.ClothingSizeStorageImpl;
import com.stepashka.bd.storage.ClothingTypeStorageImpl;
import com.stepashka.bd.storage.ObjectStorage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ClothesController extends AbstractController implements Initializable {

    @FXML
    private TableColumn<Clothes, String> brandColumn;
    @FXML
    private ComboBox<ClothingBrand> brandComboBox;
    @FXML
    private TableColumn<Clothes, String> costColumn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button insertButton;
    @FXML
    private TableColumn<Clothes, String> noteColumn;
    @FXML
    private TextField noteTextField;
    @FXML
    private TableColumn<Clothes, String> sizeColumn;
    @FXML
    private ComboBox<ClothingSize> sizeComboBox;
    @FXML
    private TextField costTextField;
    @FXML
    private TableView<Clothes> tableView;
    @FXML
    private TableColumn<Clothes, String> typeColumn;
    @FXML
    private ComboBox<ClothingType> typeComboBox;
    @FXML
    private Button updateButton;

    private ObjectStorage clothesStorage;
    private ObjectStorage clothingSizeStorage;
    private ObjectStorage clothingTypeStorage;
    private ObjectStorage clothingBrandStorage;
    private ObservableList<Clothes> observableList;
    private ObservableList<ClothingSize> sizeObservableList;
    private ObservableList<ClothingBrand> brandObservableList;
    private ObservableList<ClothingType> typeObservableList;
    private Clothes selectedItem;

    @FXML
    void onDeleteButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                clothesStorage.delete(selectedItem.getId());
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
            var item = Clothes.builder()
                    .cost(Float.parseFloat(costTextField.getText()))
                    .note(noteTextField.getText())
                    .size(sizeComboBox.getValue())
                    .brand(brandComboBox.getValue())
                    .type(typeComboBox.getValue())
                    .build();
            item.setId(clothesStorage.save(item));
            observableList.add(item);
        } catch (StorageException ex){
            showErrorAlert("Ошибка при добавлении объекта.");
        } catch (NumberFormatException ex) {
            showErrorAlert("Ошибка при конвертировании стоимости.");
        }
    }

    @FXML
    void onUpdateButton(ActionEvent event) {
        try{
            if(isSelectedItemAndId()) {
                var item = selectedItem.toBuilder()
                        .cost(Float.parseFloat(costTextField.getText()))
                        .note(noteTextField.getText())
                        .size(sizeComboBox.getValue())
                        .brand(brandComboBox.getValue())
                        .type(typeComboBox.getValue())
                        .build();
                observableList.remove(selectedItem);
                clothesStorage.update(item);
                observableList.add(item);
            } else {
                showInformationAlert("Объект таблицы не выбран.");
            }
        } catch (StorageException ex){
            showErrorAlert("Ошибка при редактировании объекта.");
        } catch (NumberFormatException ex) {
            showErrorAlert("Ошибка при конвертировании стоимости.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clothesStorage = new ClothesStorageImpl();
            clothingSizeStorage = new ClothingSizeStorageImpl();
            clothingBrandStorage = new ClothingBrandStorageImpl();
            clothingTypeStorage = new ClothingTypeStorageImpl();

            observableList = FXCollections.observableList(clothesStorage.getAll());
            sizeObservableList = FXCollections.observableList(clothingSizeStorage.getAll());
            brandObservableList = FXCollections.observableList(clothingBrandStorage.getAll());
            typeObservableList = FXCollections.observableList(clothingTypeStorage.getAll());

            costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
            noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
            typeColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getType().getCode()));
            sizeColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getSize().getCode()));
            brandColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getBrand().getCode()));

            sizeComboBox.setConverter(new StringConverter<ClothingSize>() {
                @Override
                public String toString(ClothingSize object) {
                    return object != null ? object.getCode() : "";
                }

                @Override
                public ClothingSize fromString(String string) {
                    return null;
                }
            });
            brandComboBox.setConverter(new StringConverter<ClothingBrand>() {
                @Override
                public String toString(ClothingBrand object) {
                    return object != null ? object.getCode() : "";
                }

                @Override
                public ClothingBrand fromString(String string) {
                    return null;
                }
            });
            typeComboBox.setConverter(new StringConverter<ClothingType>() {
                @Override
                public String toString(ClothingType object) {
                    return object != null ? object.getCode() : "";
                }

                @Override
                public ClothingType fromString(String string) {
                    return null;
                }
            });

            tableView.setItems(observableList);
            sizeComboBox.setItems(sizeObservableList);
            brandComboBox.setItems(brandObservableList);
            typeComboBox.setItems(typeObservableList);

            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedItem = newValue;
                    costTextField.setText(newValue.getCost().toString());
                    noteTextField.setText(newValue.getNote());
                    sizeComboBox.setValue(newValue.getSize());
                    typeComboBox.setValue(newValue.getType());
                    brandComboBox.setValue(newValue.getBrand());
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
