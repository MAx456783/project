module com.project.organizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.project.organizer to javafx.fxml;
    exports com.project.organizer;
    exports com.project.organizer.controller to javafx.fxml;
    opens com.project.organizer.controller to javafx.fxml;
    opens com.project.organizer.model to javafx.base;
}