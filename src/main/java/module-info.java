module com.example.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taskmanagerclient to javafx.fxml;
    exports com.example.taskmanagerclient;
}