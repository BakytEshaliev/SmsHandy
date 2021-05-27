package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smshandy.*;

public class HandyFormController {
    @FXML
    TextField nameTextField;

    @FXML
    TextField numberTextField;

    @FXML
    ChoiceBox<String> providerChoiceBox;

    @FXML
    ChoiceBox<String> handyTypeChoiceBox;

    Stage stage;

    DBinit db = DBinit.getInstance();

    public void initialize(){
        db.getAllProviders().forEach(p -> providerChoiceBox.getItems().add(p.getName()));
        handyTypeChoiceBox.getItems().add("TariffPlan");
        handyTypeChoiceBox.getItems().add("Prepaid");
    }

    @FXML
    public void saveHandy(){
        if (isValid()){
            String name = nameTextField.getText();
            String number = numberTextField.getText();
            Provider provider = db.findProviderByName((String) providerChoiceBox.getValue());
            SmsHandy smsHandy;
            switch ((String) handyTypeChoiceBox.getValue()){
                case "TariffPlan":
                    smsHandy = new TariffPlanSmsHandy(number, provider, name);
                    break;
                case "Prepaid":
                    smsHandy = new PrepaidSmsHandy(number, provider, name);
                    break;
                default:
                    smsHandy = null;
            }
            db.addHandy(smsHandy);
            stage.close();
        }
        else {
            System.out.println("Values aren't valid");
            System.out.println("name: " + nameTextField.getText());
            System.out.println("number: " + numberTextField.getText());
            System.out.println("number format: " + isValidNumber(numberTextField.getText()));
            System.out.println("provider: " + providerChoiceBox.getValue());
            System.out.println("type: " + handyTypeChoiceBox.getValue());
            System.out.println();
            //SHOW ERROR TO USER
        }
    }

    private boolean isValid(){
        return nameTextField.getText() != null
                && numberTextField.getText() != null
                && isValidNumber(numberTextField.getText())
                && providerChoiceBox.getValue() != null
                && handyTypeChoiceBox.getValue() != null;
    }

    private boolean isValidNumber(String number){
        return number.trim().matches("^(\\+?)([0-9]){7,13}");
    }

    @FXML
    public void cancel(){
        stage.close();
    }
}

//package smshandy.controller;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import smshandy.DBinit;
//import smshandy.Provider;
//
//public class HandyFormController extends MainController {
//
////    @FXML
////    Button saveBtn;
//    @FXML
//    TextField providerTextField;
//    @FXML
////    Button cancelBtn;
//    Stage stage;
//
//    DBinit db = DBinit.getInstance();
//    public void initialize(){
//    }
////    public void handleSaveProvider(){
////        boolean isValid = !providerTextField.getText().isBlank();
////        if (isValid) {
////            String providerName = providerTextField.getText().trim();
////            db.getAllProviders().add(new Provider(providerName));
////            stage.close();
////        }else System.out.println("not valid");
////    }
////    public void handleCancelBtn(){
////        stage.close();
////    }
//}

