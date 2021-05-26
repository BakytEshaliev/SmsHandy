package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import smshandy.DBinit;
import smshandy.Provider;

public class ProviderController {

	@FXML
	private TableView<Provider> providerTable;
	@FXML
	private TableColumn<Provider, String> providerCol;

	DBinit db = DBinit.getInstance();

	public void initialize() {
		loadAllProviders();
		providerCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
	}

	private void loadAllProviders() {
		providerTable.setItems(FXCollections.observableArrayList(db.getAllProviders()));
	}

	public void deleteProviderBtn() {
		int selectedIndex = providerTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			providerTable.getItems().remove(selectedIndex);
		}
	}
}
