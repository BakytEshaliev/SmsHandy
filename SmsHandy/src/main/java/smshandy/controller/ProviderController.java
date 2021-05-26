package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import smshandy.DBinit;
import smshandy.Provider;
import smshandy.SmsHandy;

public class ProviderController {
    @FXML
    private TableView<Provider> providerTable;
    @FXML
    private TableColumn<Provider,String> providerNameCol;

    DBinit db = DBinit.getInstance();
    public void initialize(){
        providerTable.setItems(FXCollections.observableArrayList(db.getAllProviders()));
        providerNameCol.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getName()));

    }
}
