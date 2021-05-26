package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import smshandy.DBinit;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;

public class ProviderController {

    @FXML
    private TableView<Provider> providerTable;
    @FXML
    private TableColumn<Provider,String> providerCol;

    DBinit db = DBinit.getInstance();
    public void initialize(){
        loadAllProviders();
        providerCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
    }

    public void deleteProviderBtn(){
        Provider provider = providerTable.getSelectionModel().getSelectedItem();
        // delete provider
        //and set null to owners of provider
        provider.getSubscriber().forEach((k,v)->{
            v.setProvider(null);
            if (v.getClass().equals(TariffPlanSmsHandy.class)) {
                ((TariffPlanSmsHandy)v).setRemainingFreeSms(0);
            }
        });
        db.getAllProviders().remove(provider);

        loadAllProviders();
    }

    private void loadAllProviders(){
        providerTable.setItems(FXCollections.observableArrayList(db.getAllProviders()));
    }
}
