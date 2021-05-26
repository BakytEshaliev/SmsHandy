package smshandy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import smshandy.controller.HandyController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        load(primaryStage);
    }
    public void load(Stage stage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }


}
