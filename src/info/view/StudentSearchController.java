package info.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class StudentSearchController {

    private Stage dialogStage;
    private boolean okClicked = false;
    private StudentOverviewController controller;

    @FXML
    private TextField keyField;

    @FXML
    private void initialize(){
    }

    public void setController(StudentOverviewController controller){
        this.controller = controller;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }


    public String getKey(){return keyField.getText();}

    @FXML
    private void searchNext(){
//        System.out.println(keyField.getText());
        if(keyField.getText()!=null&&keyField.getText().length()!=0){
            okClicked = true;
            controller.selectData(keyField.getText());
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
