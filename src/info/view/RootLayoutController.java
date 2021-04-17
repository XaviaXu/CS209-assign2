package info.view;

import info.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {
    private MainApp mainApp;
    private StudentOverviewController controller;
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
    public void setController(StudentOverviewController controller){
        this.controller = controller;
    }

    @FXML
    private void handleNew(){
        controller.addTable();
    }
}
