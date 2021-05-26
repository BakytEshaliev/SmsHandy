package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import smshandy.DBinit;
import smshandy.SmsHandy;
import smshandy.TariffPlanSmsHandy;



public class HandyController {

    @FXML
    private TableView<SmsHandy> handyTable;
    @FXML
    private TableColumn<SmsHandy,String> handyCol;
    @FXML
    private TableColumn<SmsHandy,String> artCol;

    @FXML
    private Label providerValLabel;
    @FXML
    private Label numberValLabel;   
    @FXML
    private Label handyValLabel; 
    DBinit db = DBinit.getInstance();

    public void initialize(){
        handyTable.setItems(FXCollections.observableArrayList(db.getAllHandy()));

        handyTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedItemDetails(newValue));
        handyCol.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getName()));
        artCol.setCellValueFactory(cell -> new SimpleStringProperty(getArthandy(cell.getValue())));
    }

    private void setSelectedItemDetails(SmsHandy handy) {
        if (handy !=null) {
            providerValLabel.setText(handy.getProvider().getName());
            numberValLabel.setText(handy.getNumber());
            handyValLabel.setText(handy.getName());
        }else {
            
            
        }
    }

    private String getArthandy(SmsHandy smsHandy){
        if (smsHandy.getClass().equals(TariffPlanSmsHandy.class))
            return "Tariff Plan";
        else return "Prepaid Plan";
    }


}
