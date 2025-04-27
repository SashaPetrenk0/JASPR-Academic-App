module org.jaspr.hr.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;


    opens org.jaspr.hr.demo to javafx.fxml, com.google.gson;
    exports org.jaspr.hr.demo;
    exports org.jaspr.hr.demo.users;
    opens org.jaspr.hr.demo.users to com.google.gson, javafx.fxml;
}