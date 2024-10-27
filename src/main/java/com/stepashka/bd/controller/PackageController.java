package com.stepashka.bd.controller;

import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.storage.PackageMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class PackageController extends AbstractController implements Initializable {
    @FXML
    private Button executeButton;

    @FXML
    private TextArea textArea;

    private PackageMode packageStorage;

    @FXML
    void executeButton(ActionEvent event) {
        try{
            var text = textArea.getText();
            var queries = text.split(";");
            for(int i = 0; i < queries.length; i++){
                packageStorage.executeSQL(queries[i]);
            }
            showInformationAlert("Запрос успешно выполнен.");
        } catch (StorageException ex){
            showErrorAlert("Ошибка при выполнении запроса.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        packageStorage = new PackageMode();
    }
}
