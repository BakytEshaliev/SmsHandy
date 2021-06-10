package smshandy.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smshandy.*;

public class HandyController extends MainController {

	@FXML
	private TableView<SmsHandy> handyTable;
	@FXML
	private TableColumn<SmsHandy, String> handyCol;
	@FXML
	private TableColumn<SmsHandy, String> artCol;

	@FXML
	ChoiceBox<String> providersCB;

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
	private Button loadCreditButton;
	@FXML
	private Button sendSmsButton;
	DBinit db = DBinit.getInstance();

	@Override
	public void initialize() {
		setSelectedItemDetails(null);
		loadAllPhones();
		handyTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedItemDetails(newValue));
		handyCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		artCol.setCellValueFactory(cell -> new SimpleStringProperty(getArtHandy(cell.getValue())));
		providersCB.getItems().addAll(db.getAllProviders().stream().map(Provider::getName).collect(Collectors.toList()));
	}

	private void disableButtons(SmsHandy handy) {
		loadCreditButton.setDisable(handy instanceof TariffPlanSmsHandy || handy.getProvider() == null);
		sendSmsButton.setDisable(handy.getProvider() == null);
	}
	/**
	 * Shows the details of a smshandy.
	 *
	 * @param handy the detailed handy
	 */
	private void setSelectedItemDetails(SmsHandy handy) {
		if (handy != null) {
			disableButtons(handy);
			if (handy.getProvider() != null) {
				if (handy.getClass().equals(TariffPlanSmsHandy.class)) {
					balanceLabel.setText("Remaining Free Sms");
					balanceValLabel.setText(((TariffPlanSmsHandy) handy).getRemainingFreeSms() + "");
				} else {
					balanceLabel.setText("Guthaben");
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
		return (smsHandy instanceof TariffPlanSmsHandy) ? "Tariff Plan" : "Prepaid Plan";
	}

	@FXML
	/**
	 * Deletes a phone.
	 */
	public void deletePhoneBtn() {

		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null) {
			db.findProviderByName(smsHandy.getProvider().getName()).deleteSmsHandy(smsHandy);
			db.deletePhone(smsHandy);
			System.out.println(db.getAllHandy().size());

			loadAllPhones();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("You must select a phone.");

			alert.showAndWait();
		}
	}

	@FXML
	/**
	 * Changes the provider of a handy.
	 */
	public void changeProviderBtn() {
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null && providersCB.getValue() != null) {
			try {
				smsHandy.setProvider(db.findProviderByName(providersCB.getValue()));
				setSelectedItemDetails(smsHandy);
			} catch (NullPointerException e) {
				/*
				 * SHOW ERROR TO USER
				 */
			}
		}
	}
	/**
	 * Loads all handy in the table.
	 */
	public void loadAllPhones() {
		handyTable.setItems(FXCollections.observableArrayList(db.getAllHandy()));
	}
	/**
	 * Adds a new handy to the table.
	 */
	public void addHandyBtn() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/handy_form.fxml"));
			AnchorPane pane = loader.load();
			HandyFormController controller = loader.getController();
			Stage stage = new Stage();
			controller.stage = stage;
			stage.setTitle("Create new Handy");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(getPrimaryStage());
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.showAndWait();
			loadAllPhones();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Sends a message to another handy.
	 */
	public void sendSms() {
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null) {
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/send_sms.fxml"));
				AnchorPane pane = loader.load();
				SendSmsController controller = loader.getController();
				Stage stage = new Stage();
				controller.setFromPhone(smsHandy);
				controller.setParentStage(stage);
				controller.loadData();
				stage.setTitle("Send Sms");
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(getPrimaryStage());
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.showAndWait();
				loadAllPhones();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("You must select a phone.");

			alert.showAndWait();
		}

	}
	
	/**
	 * Recharges the balance of a handy.
	 */
	public void loadCredits() {
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null) {
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/load_credit.fxml"));
				AnchorPane pane = loader.load();
				LoadCreditController controller = loader.getController();
				controller.setPhone((PrepaidSmsHandy) smsHandy);
				Stage stage = new Stage();
				controller.setPrimaryStage(stage);
				stage.setTitle("Loading credits");
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(getPrimaryStage());
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.showAndWait();
				loadAllPhones();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Shows all received sms of a handy.
	 */
	public void showAllReceivedSms() {
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null) {
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/all_received_sms.fxml"));
				AnchorPane pane = loader.load();
				AllReceivedSmsController controller = loader.getController();
				Stage stage = new Stage();
				controller.setPhone(smsHandy);
				controller.loadData();

				stage.setTitle("All received sms");
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(getPrimaryStage());
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Shows all sended sms of a handy.
	 */
	public void showAllSentSms() {
		SmsHandy smsHandy = handyTable.getSelectionModel().getSelectedItem();
		if (smsHandy != null) {
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/all_sent_sms.fxml"));
				AnchorPane pane = loader.load();

				AllSentSmsController controller = loader.getController();
				Stage stage = new Stage();
				controller.setPrimaryStage(stage);
				controller.setPhone(smsHandy);
				controller.loadData();

				stage.setTitle("All sent sms");
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(getPrimaryStage());
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
