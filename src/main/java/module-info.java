module org.jaspr.hr.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.jaspr.hr.demo to javafx.fxml;
    exports org.jaspr.hr.demo;
}