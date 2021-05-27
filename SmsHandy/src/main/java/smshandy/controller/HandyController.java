package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import smshandy.DBinit;
import smshandy.PrepaidSmsHandy;
import smshandy.Provider;
import smshandy.SmsHandy;
import smshandy.TariffPlanSmsHandy;

public class HandyController {
	Provider provider;
	@FXML
	private TableView<SmsHandy> handyTable;
	@FXML
	private TableColumn<SmsHandy, String> handyCol;
	@FXML
	private TableColumn<SmsHandy, String> artCol;

	@FXML
	ChoiceBox<String> providersCB;
	@FXML
	ChoiceBox<String> providersCB1;

	@FXML
	private Label providerValLabel;
	@FXML
	private Label numberValLabel;
	@FXML
	private Label handyValLabel;
	@FXML
	private Label balanceValLabel;
	@FXML
	private Label balanceLabel;

	@FXML
	TextField ownerNameTextField;
	@FXML
	private TextField ownerNumberTextField;

	@FXML
	private RadioButton prepaidHandy;
	@FXML
	private RadioButton tariffPlanHandy;

	DBinit db = DBinit.getInstance();

	public void initialize() {
		setSelectedItemDetails(null);
		loadAllPhones();
		handyTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedItemDetails(newValue));
		handyCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		artCol.setCellValueFactory(cell -> new SimpleStringProperty(getArtHandy(cell.getValue())));
		providersCB.setValue("Providers");
		db.getAllProviders().forEach(e -> providersCB.getItems().add(e.getName()));
	}

	private void setSelectedItemDetails(SmsHandy handy) {
		if (handy != null) {
			System.out.println(handy);

			if (handy.getProvider() != null) {
				if (handy.getClass().equals(TariffPlanSmsHandy.class)) {
					balanceLabel.setText("Guthaben");
					balanceValLabel.setText(((TariffPlanSmsHandy) handy).getRemainingFreeSms() + "");
				} else {
					balanceLabel.setText("Remaining Free Sms");
					balanceValLabel.setText(((PrepaidSmsHandy) handy).getBalance() + "");
				}
				providerValLabel.setText(handy.getProvider().getName());
			} else {
				providerValLabel.setText("No Provider");
				balanceValLabel.setText("");
			}

			numberValLabel.setText(handy.getNumber());
			handyValLabel.setText(handy.getName());
		} else {
			providerValLabel.setText("");
			numberValLabel.setText("");
			handyValLabel.setText("");
			balanceValLabel.setText("");

		}
	}

	private String getArtHandy(SmsHandy smsHandy) {
		if (smsHandy.getClass().equals(TariffPlanSmsHandy.class))
			return "Tariff Plan";
		else
			return "Prepaid Plan";
	}

	@FXML
	public void deletePhoneBtn() {
		System.out.println("Before " + db.getAllHandy().size());
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		db.findProviderByName(smsHandy.getProvider().getName()).deleteSmsHandy(smsHandy);
		db.deletePhone(smsHandy);
		System.out.println("After " + db.getAllHandy().size());
		System.out.println(db.getAllHandy().size());

		loadAllPhones();
	}

	@FXML
	public void changeProviderBtn() {
		try {
			SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
			smsHandy.setProvider(db.findProviderByName(providersCB.getValue()));
			setSelectedItemDetails(smsHandy);
		} catch (NullPointerException e) {
			/*
			 * SHOW ERROR TO USER
			 */
		}
	}

	@FXML
	private boolean prepaidHandySelected() {
		if (prepaidHandy.isArmed()) {
			prepaidHandy.setSelected(true);
			return true;
		}

		if (prepaidHandy.isSelected() == true && prepaidHandy.isArmed()) {
			prepaidHandy.setSelected(false);
			return false;
		}
		return false;
	}

	@FXML
	private boolean tariffPlanHandySelected() {
		if (prepaidHandy.isArmed()) {
			prepaidHandy.setSelected(true);
			return true;
		}
		if (prepaidHandy.isSelected() == true && prepaidHandy.isArmed()) {
			prepaidHandy.setSelected(false);
			return false;
		}
		return false;
	}

	@FXML
	public void addPhone() {
		provider = new Provider(providersCB.getValue());

		if (prepaidHandySelected() == true) {
			db.getAllHandy()
					.add(new PrepaidSmsHandy(ownerNumberTextField.getText(), provider, ownerNameTextField.getText()));
			loadAllPhones();
		}
		if (tariffPlanHandySelected() == true) {
			db.getAllHandy().add(
					new TariffPlanSmsHandy(ownerNumberTextField.getText(), provider, ownerNumberTextField.getText()));
			loadAllPhones();
		}

	}

	private void loadAllPhones() {
		handyTable.setItems(FXCollections.observableArrayList(db.getAllHandy()));
	}

}
