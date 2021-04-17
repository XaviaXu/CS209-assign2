package info.view;

import info.MainApp;
import info.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentOverviewController {

    private MainApp mainApp;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
//    @FXML
//    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label gpaLabel;
    @FXML
    private Label creditLabel;
    @FXML
    private Label birthdayLabel;

    public StudentOverviewController(){

    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        studentTable.setItems(mainApp.getStudentData());
    }



}
