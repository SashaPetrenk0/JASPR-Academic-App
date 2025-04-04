module org.jaspr.hr.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.jaspr.hr.demo to javafx.fxml;
    exports org.jaspr.hr.demo;
    exports org.jaspr.hr.demo.model;
    opens org.jaspr.hr.demo.model to javafx.fxml;
    exports org.jaspr.hr.demo.controller;
    opens org.jaspr.hr.demo.controller to javafx.fxml;
}