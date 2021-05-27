package smshandy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import smshandy.controller.MainController;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/main.fxml"));
		Parent root = loader.load();
		MainController mainController = loader.getController();
		mainController.setMain(this);
		mainController.setPrimaryStage(stage);
		stage.setScene(new Scene(root));
		stage.show();
	}

}
