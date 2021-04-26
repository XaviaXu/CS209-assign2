package info.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;

public class StudentSearchController {

    private Stage dialogStage;
    private boolean okClicked = false;

//    private StudentOverviewController controller;

    @FXML
    private TextField keyField;

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    private void searchNext(){
        if(keyField.getText()!=null){
            okClicked = true;
        }else{
            okClicked = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Input");
            alert.setHeaderText("No Input Data");
            alert.setContentText("Please input a valid key for selection.");
            alert.showAndWait();
        }
    }
}
