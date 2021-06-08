package smshandy.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import smshandy.PrepaidSmsHandy;
import smshandy.SmsHandy;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadCreditController extends MainController{

    @FXML
    private TextField amountTextField;

    private PrepaidSmsHandy phone;

    public void initialize(){

    }
    public void handleCancel(){
        getPrimaryStage().close();
    }
    public void handleOk(){
        String amount = amountTextField.getText();
        boolean valid = !amount.isBlank() && amount.matches("\\d+");
        if (valid){
            int amountCredit = Integer.parseInt(amount);
            if (amountCredit > 0){
                phone.deposit(amountCredit);
                getPrimaryStage().close();
            }
        }
    }

    public void setPhone(PrepaidSmsHandy phone) {
        this.phone = phone;
    }
}
