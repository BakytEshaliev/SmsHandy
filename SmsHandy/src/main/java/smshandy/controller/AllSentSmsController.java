package smshandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import smshandy.DBinit;
import smshandy.Message;
import smshandy.SmsHandy;

public class AllSentSmsController extends MainController{

    private SmsHandy phone;
    @FXML
    private TableView<Message> recipientTable;

    @FXML
    private TableColumn<Message, String> recipientCol;

    @FXML
    private TextArea contentValTextArea;
    @FXML
    private Label dateValLabel;

    private DBinit db = DBinit.getInstance();
	
	/**
	 * Fills the table with data.
	 */
    public void initialize(){
        dateValLabel.setText("");
        recipientCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTo()));

        recipientTable.getSelectionModel().selectedItemProperty()
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
        recipientTable.setItems(FXCollections.observableArrayList(phone.getSent()));

    }

    public void setPhone(SmsHandy phone) {
        this.phone = phone;
    }

}
