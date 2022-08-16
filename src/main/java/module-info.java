module com.example._2048 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens com.example._2048 to javafx.fxml;
    exports com.example._2048;
}