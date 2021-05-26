module smshandy_module {
    requires javafx.fxml;
    requires javafx.controls;
    opens smshandy to javafx.fxml;


    exports smshandy;
    exports smshandy.controller;
    opens smshandy.controller to javafx.fxml;

}