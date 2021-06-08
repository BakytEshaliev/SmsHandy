package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import smshandy.Main;

import java.io.IOException;

public  class MainController {
    private Stage primaryStage;
    private Main main;
    @FXML
    private TabPane tabPane;


    @FXML private HandyController phonesController;
    @FXML private ProviderController providersController;

    public void initialize() throws IOException {
        tabPane.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            switch (newValue.getId()) {
                case "handyTab":
                    getPhonesController().loadAllPhones();
                    break;
                case "providerTab":
                    getProvidersController().loadAllProviders();
                    break;
            }
        }));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public HandyController getPhonesController() {
        return phonesController;
    }

    public ProviderController getProvidersController() {
        return providersController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
