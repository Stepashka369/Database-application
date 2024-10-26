module com.stepashka.bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j; // добавьте эту строку
//    requires ch.qos.logback; // добавьте линию для Logback, если используете его
    requires static lombok;


    opens com.stepashka.bd to javafx.fxml, org.slf4j;
    opens com.stepashka.bd.model to javafx.base;
    exports com.stepashka.bd;
    exports com.stepashka.bd.controller;
    opens com.stepashka.bd.controller to javafx.fxml, org.slf4j;
}