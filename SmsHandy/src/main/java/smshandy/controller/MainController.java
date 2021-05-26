package smshandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import smshandy.Main;

public class MainController {
    Stage stage;
    Main main;
    @FXML
    TabPane tabPane;
    @FXML
    Tab providerTab;
    @FXML
    Tab handyTab;

    public void initialize(){
//        tabPane.getSelectionModel().selectedItemProperty().addListener();

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
