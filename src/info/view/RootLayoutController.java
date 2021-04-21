package info.view;

import info.MainApp;
import info.model.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;

public class RootLayoutController {
    private MainApp mainApp;
    private StudentOverviewController controller;

    private static TabPane tabPane;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
    public void setController(StudentOverviewController controller){
        this.controller = controller;
    }

    public static void setTabPane(TabPane tabPane){
        RootLayoutController.tabPane = tabPane;
    }

    @FXML
    private void handleNew(){
        controller.addStudentTable();
    }

    @FXML
    private void handleSave(){
        Map<Tab, ObservableList<Student>> studentData = controller.getStudentData();
        Tab selected = tabPane.getSelectionModel().getSelectedItem();


        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".csv")) {
                file = new File(file.getPath() + ".csv");
            }
//            mainApp.savePersonDataToFile(file);
        }

    }

    @FXML
    private void handleOpen(){

    }

    @FXML
    private void handleSearch(){

    }

}
