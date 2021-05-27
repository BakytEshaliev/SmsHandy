package smshandy.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smshandy.DBinit;
import smshandy.Provider;

public class ProviderFormController extends MainController {

    @FXML
    Button saveBtn;
    @FXML
    TextField providerTextField;
    @FXML
    Button cancelBtn;
    Stage stage;

    DBinit db = DBinit.getInstance();
    public void initialize(){
    }
    public void handleSaveProvider(){
        boolean isValid = !providerTextField.getText().isBlank();
        if (isValid) {
            String providerName = providerTextField.getText().trim();
            db.getAllProviders().add(new Provider(providerName));
            stage.close();
        }else System.out.println("not valid");
    }
    public void handleCancelBtn(){
        stage.close();
    }
}
