package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smshandy.DBinit;
import smshandy.PrepaidSmsHandy;
import smshandy.Provider;
import smshandy.SmsHandy;
import smshandy.TariffPlanSmsHandy;

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

	public void initialize() {
		db.getAllProviders().forEach(p -> providerChoiceBox.getItems().add(p.getName()));
		handyTypeChoiceBox.getItems().add("TariffPlan");
		handyTypeChoiceBox.getItems().add("Prepaid");
	}

	@FXML
	public void saveHandy() {
		if (isValid()) {
			String name = nameTextField.getText();
			String number = numberTextField.getText();
			Provider provider = db.findProviderByName(providerChoiceBox.getValue());
			SmsHandy smsHandy;
			switch (handyTypeChoiceBox.getValue()) {
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
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("Values aren't valid" + "\n" + "name: " + nameTextField.getText() + "\n" + "number: "
					+ numberTextField.getText() + "\n" + "number format: " + isValidNumber(numberTextField.getText())
					+ "\n" + "provider: " + providerChoiceBox.getValue() + "\n" + "type: "
					+ handyTypeChoiceBox.getValue());

			alert.showAndWait();
		}
	}

	private boolean isValid() {
		return nameTextField.getText() != null && numberTextField.getText() != null
				&& isValidNumber(numberTextField.getText()) && providerChoiceBox.getValue() != null
				&& handyTypeChoiceBox.getValue() != null;
	}

	private boolean isValidNumber(String number) {
		return number.trim().matches("^(\\+?)([0-9]){7,13}");
	}

	@FXML
	public void cancel() {
		stage.close();
	}
}
