package smshandy.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import smshandy.DBinit;
import smshandy.Message;
import smshandy.SmsHandy;

import java.util.Date;
import java.util.stream.Collectors;

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

    public void initialize(){

    }
    public void loadData(){
        phonesChoiceBox.getItems()
                .addAll(db.getAllHandy()
                        .stream()
                        .map(SmsHandy::getNumber)
                        .filter(p->!p.equals(fromPhone.getNumber()))
                        .collect(Collectors.toList()));
    }
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

    public void handleCancel(){
        parentStage.close();
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setFromPhone(SmsHandy fromPhone) {
        this.fromPhone = fromPhone;
    }
    private void showAnimation(){

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
        path.getElements().add(new MoveTo(-100,0));
        path.getElements().add(new LineTo(100,0));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setOnFinished(event -> stage.close());
        pathTransition.play();
        stage.showAndWait();

    }
}
