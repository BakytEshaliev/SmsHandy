package smshandy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import smshandy.Main;
import smshandy.controller.HandyController;
import smshandy.controller.ProviderController;

import java.io.IOException;

public class MainController {
    Stage stage;
    Main main;
    @FXML
    TabPane tabPane;
    @FXML
    Tab providerTab;
    @FXML
    Tab handyTab;

    @FXML
    AnchorPane providerTabContent;
    @FXML
    AnchorPane handyTabContent;

    HandyController handyController;
    ProviderController providerController;

    public void initialize() throws IOException {
        handyTab.setContent(FXMLLoader.load(getClass().getResource("view/phones.fxml")));
        tabPane.getSelectionModel().select(handyTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            switch (newValue.getId()){
                case "handyTab":{

                    try {
                        handyTab.setContent(FXMLLoader.load(getClass().getResource("view/phones.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "providerTab":{
                    try {
                        providerTab.setContent(FXMLLoader.load(getClass().getResource("view/provider.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }));

    }
    @FXML
    public void loadProvider(){

    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
