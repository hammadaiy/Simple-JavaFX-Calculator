module com.example.three {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.three to javafx.fxml;
    exports com.example.three;
}