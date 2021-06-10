package smshandy.controller;

import java.util.stream.Collectors;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import smshandy.DBinit;
import smshandy.SmsHandy;

public class SendSmsController {

	@FXML
	private ChoiceBox<String> phonesChoiceBox;
	@FXML
	private TextArea smsContentTextArea;

	private Stage parentStage;
	private SmsHandy fromPhone;
	@FXML
	private Label messageToUpdate;

	private DBinit db = DBinit.getInstance();

	public void initialize() {

	}
	/**
	 * Add Data to the Choicebox.
	 */
	public void loadData() {
		phonesChoiceBox.getItems().addAll(db.getAllHandy().stream().map(SmsHandy::getNumber)
				.filter(p -> !p.equals(fromPhone.getNumber())).collect(Collectors.toList()));
	}
	/**
	 * Send a Message with a animation to a receiver.
	 */
	public void handleSend() {
		String number = phonesChoiceBox.getValue();
		String content = smsContentTextArea.getText().trim();

		if (number != null && !number.isEmpty() && !content.isEmpty()) {

			showAnimation();
			fromPhone.sendSms(number, content);
			System.out.println("Message sent");
			parentStage.close();
		}
	}
	/**
	 * Close the dialog.
	 */
	public void handleCancel() {
		parentStage.close();
	}

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

	public void setFromPhone(SmsHandy fromPhone) {
		this.fromPhone = fromPhone;
	}
	/**
	 * shows a animation 
	 */
	private void showAnimation() {

		Circle circle = new Circle(20);
		circle.setFill(Color.RED);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(circle);

		Stage stage = new Stage();
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(borderPane, 400, 200);
		stage.setScene(scene);

		Path path = new Path();
		path.getElements().add(new MoveTo(-100, 0));
		path.getElements().add(new LineTo(100, 0));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(1500));
		pathTransition.setPath(path);
		pathTransition.setNode(circle);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(1);
		pathTransition.setOnFinished(event -> stage.close());
		pathTransition.play();
		stage.showAndWait();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Message sent successfully");

		alert.showAndWait();

	}
}
