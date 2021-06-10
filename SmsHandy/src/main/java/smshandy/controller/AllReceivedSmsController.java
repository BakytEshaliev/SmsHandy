package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import smshandy.DBinit;
import smshandy.Message;
import smshandy.SmsHandy;

import java.text.SimpleDateFormat;

public class AllReceivedSmsController {
    @FXML
    private TableView<Message> senderTable;
    @FXML
    private TableColumn<Message,String> senderCol;
    @FXML
    private TextArea contentValTextArea;
    @FXML
    private Label dateValLabel;
    @FXML
    private Label senderNameValLabel;


    private SmsHandy phone;
    private DBinit db = DBinit.getInstance();
    public void initialize(){
        dateValLabel.setText("");
        senderCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFrom()));

        senderTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setSelectedItemDetails(newValue));
    }
    private void setSelectedItemDetails(Message msg) {
        if (msg != null){
            dateValLabel.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(msg.getDate()));
            contentValTextArea.setText(msg.getContent());
            senderNameValLabel.setText(msg.getFrom());
        }else {
            dateValLabel.setText("");
            contentValTextArea.setText("");
            senderNameValLabel.setText("");
        }
    }
    public void loadData(){
       senderTable.setItems(FXCollections.observableArrayList(phone.getReceived()));
    }

    public void setPhone(SmsHandy phone) {
        this.phone = phone;
    }
}
