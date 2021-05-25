package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import smshandy.Main;
import smshandy.SmsHandy;
import smshandy.TariffPlanSmsHandy;



public class HandyController {

    @FXML
    private TableView<SmsHandy> handyTable;
    @FXML
    private TableColumn<SmsHandy,String> handyCol;
    @FXML
    private TableColumn<SmsHandy,String> artCol;

    public void initialize(){
        handyTable.setItems(FXCollections.observableArrayList(Main.getAllHandy()));
        handyCol.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getName()));
        artCol.setCellValueFactory(cell -> new SimpleStringProperty(getArthandy(cell.getValue())));
    }

    private String getArthandy(SmsHandy smsHandy){
        if (smsHandy.getClass().getName().equals(TariffPlanSmsHandy.class)){
            return "tariff";
        }else return "prepaid";
    }
}
