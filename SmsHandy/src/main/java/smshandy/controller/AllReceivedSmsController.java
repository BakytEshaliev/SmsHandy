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

public class AllReceivedSmsController {
    @FXML
    private TableView<Message> senderTable;
    @FXML
    private TableColumn<Message,String> senderCol;
    @FXML
    private TextArea contentValTextArea;
    @FXML
    private Label dateValLabel;

    private SmsHandy phone;
    private DBinit db = DBinit.getInstance();
	
	/**
	 * Fills the table with data.
	 */
    public void initialize(){
        dateValLabel.setText("");
        senderCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFrom()));

        senderTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setSelectedItemDetails(newValue));
    }
	
	/**
	 * Shows the details from the message.
	 */
    private void setSelectedItemDetails(Message msg) {
        if (msg != null){
            dateValLabel.setText(msg.getDate().toString());
            contentValTextArea.setText(msg.getContent());
        }else {
            dateValLabel.setText("");
            contentValTextArea.setText("");
        }
    }
	
	/**
	 * Adds data to the to the sender table.
	 */
    public void loadData(){
       senderTable.setItems(FXCollections.observableArrayList(phone.getReceived()));
    }

    public void setPhone(SmsHandy phone) {
        this.phone = phone;
    }
}
