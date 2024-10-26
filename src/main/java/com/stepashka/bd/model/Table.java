package com.stepashka.bd.model;

import java.util.List;


public enum Table {
    CLOTHING_SIZES(List.of("code", "note"), "clothing-sizes.fxml"),
    CLIENTS(List.of("name", "surname", "email"), "clients.fxml"),
    CLOTHES(List.of("cost", "note"), ""),
    CLOTHING_BRANDS(List.of("code", "note"), ""),
    CLOTHING_TYPES(List.of("code", "note"), ""),
    STOCKS(List.of("town", "address"), "");

    private final List<String> columns;
    private final String fxmlFileName;

    Table(List<String> columns, String fxmlFileName) {
        this.columns = columns;
        this.fxmlFileName = fxmlFileName;
    }

    public List getColumns() {
        return columns;
    }

    public String getFxmlFileName() {
        return fxmlFileName;
    }
}
