package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smshandy.*;
import smshandy.controller.MainController;

import java.io.IOException;

public class ProviderController extends MainController {

	@FXML
	private TableView<Provider> providerTable;
	@FXML
	private TableColumn<Provider, String> providerCol;

	DBinit db = DBinit.getInstance();

	/**
	 * Initializes the controller class and initialize the provider table.
	 */
	public void initialize() {
		loadAllProviders();
		providerCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
	}

	/**
	 * delete a Provider in the table
	 */
	public void deleteProviderBtn() {
		Provider provider = providerTable.getSelectionModel().getSelectedItem();
		// delete provider
		// and set null to owners of provider
		if (provider!=null) {

			provider.getSubscriber().forEach((k, v) -> v.deleteProvider());
			db.getAllProviders().remove(provider);
			loadAllProviders();
		}

	}

	@FXML
	/**
	 * 
	 */
	public void addProviderBtn() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/provider_form.fxml"));
//            AnchorPane pane = FXMLLoader.load(Main.class.getResource("view/provider_form.fxml"));
			AnchorPane pane = loader.load();
			ProviderFormController controller = loader.getController();
			Stage stage = new Stage();
			controller.stage = stage;
			stage.setTitle("Add Provider");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(getPrimaryStage());
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.showAndWait();
			loadAllProviders();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Set all providers in the table.
	 */
	public void loadAllProviders() {
		providerTable.setItems(FXCollections.observableArrayList(db.getAllProviders()));
	}
}
