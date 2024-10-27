module com.stepashka.bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j; // добавьте эту строку
//    requires ch.qos.logback; // добавьте линию для Logback, если используете его
    requires static lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;


    opens com.stepashka.bd to javafx.fxml, org.slf4j;
    opens com.stepashka.bd.entity to javafx.base;
    exports com.stepashka.bd;
    exports com.stepashka.bd.controller;
    opens com.stepashka.bd.controller to javafx.fxml, org.slf4j;
}