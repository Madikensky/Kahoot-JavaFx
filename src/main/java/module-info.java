module com.example.kahoot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kahoot to javafx.fxml;
    exports com.example.kahoot;
}