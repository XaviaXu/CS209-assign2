package info.view;

import info.MainApp;
import info.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;
import util.DateUtil;

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
        showStudentDetail(null);

        studentTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showStudentDetail(newValue))
        );


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

    private void showStudentDetail(Student student){
        if(student!=null){
            idLabel.setText(Integer.toString(student.getID()));
            nameLabel.setText(student.getName());
            birthdayLabel.setText(DateUtil.format(student.getBirthday()));
        }else{
            idLabel.setText("");
            nameLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteStudent(){
        int selectIndex = studentTable.getSelectionModel().getSelectedIndex();
        if(selectIndex>=0){
            studentTable.getItems().remove(selectIndex);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }



}
