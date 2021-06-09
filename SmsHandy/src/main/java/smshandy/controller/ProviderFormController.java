package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	@Override
	public void initialize() {
	}

	public void handleSaveProvider() {
		boolean isValid = !providerTextField.getText().isBlank();
		if (isValid) {
			String providerName = providerTextField.getText().trim();
			db.getAllProviders().add(new Provider(providerName));
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("You must write in the textfield.");

			alert.showAndWait();
		}
	}

	public void handleCancelBtn() {
		stage.close();
	}
}
