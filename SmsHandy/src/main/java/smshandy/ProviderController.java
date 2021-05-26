package smshandy;

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
import smshandy.DBinit;
import smshandy.Main;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;
import smshandy.controller.MainController;

import java.io.IOException;

public class ProviderController extends MainController {

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
        provider.getSubscriber().forEach((k,v)-> v.deleteProvider());
        db.getAllProviders().remove(provider);

        loadAllProviders();
    }
    public void addProviderBtn(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/providerForm.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Provider");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadAllProviders(){
        providerTable.setItems(FXCollections.observableArrayList(db.getAllProviders()));
    }
}
