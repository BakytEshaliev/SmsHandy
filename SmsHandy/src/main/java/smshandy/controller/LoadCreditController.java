package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import smshandy.PrepaidSmsHandy;

public class LoadCreditController extends MainController{

    @FXML
    private TextField amountTextField;

    private PrepaidSmsHandy phone;

    public void initialize(){

    }
	/**
	 * Close the dialog.
	 */
    public void handleCancel(){
        getPrimaryStage().close();
    }
	/**
	 * Adds the amount of credits to the balance of the handy.
	 */
    public void handleOk(){
        String amount = amountTextField.getText();
        boolean valid = !amount.isBlank() && amount.matches("\\d+");
        if (valid){
            int amountCredit = Integer.parseInt(amount);
            if (amountCredit > 0){
                phone.deposit(amountCredit);
                getPrimaryStage().close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("The amount must be a positive integer and not empty ");
            alert.showAndWait();
        }
    }
    /**
	 * Set a new Prepaid handy.
	 */
    public void setPhone(PrepaidSmsHandy phone) {
        this.phone = phone;
    }
}
