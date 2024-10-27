package com.stepashka.bd.controller;

import com.stepashka.bd.HelloApplication;
import com.stepashka.bd.entity.Table;
import com.stepashka.bd.storage.ObjectStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MainController {
    private ObjectStorage clothingSizeStorage;

    @FXML
    private Button clientsButton;
    @FXML
    private Button clothesButton;
    @FXML
    private Button clothingBrandsButton;
    @FXML
    private Button clothingSizesButton;
    @FXML
    private Button clothingTypesButton;
    @FXML
    private Button stockButton;

    @FXML
    void onClientsButton(ActionEvent event) {
        newWindow(Table.CLIENTS);
    }

    @FXML
    void onClothesButton(ActionEvent event) {
        newWindow(Table.CLOTHES);
    }

    @FXML
    void onClothingBrandsButton(ActionEvent event) {
        newWindow(Table.CLOTHING_BRANDS);
    }

    @FXML
    void onClothingSizesButton(ActionEvent event) {
        newWindow(Table.CLOTHING_SIZES);
    }

    @FXML
    void onClothingTypesButton(ActionEvent event) {
        newWindow(Table.CLOTHING_TYPES);
    }

    @FXML
    void onStockButton(ActionEvent event) {
        newWindow(Table.STOCKS);
    }

    private void newWindow(Table table) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(table.getFxmlFileName()));
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            stage.setTitle(table.name());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch(IOException ex){
            //TODO write exception handler
        }
    }
}

