module smshandy_module {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.junit.jupiter.api;

//    opens smshandy.view to javafx.fxml;
    opens smshandy to javafx.fxml;
//    opens smshandy.controller to javafx.fxml;
    exports smshandy;
}