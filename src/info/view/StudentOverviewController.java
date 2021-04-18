package info.view;

import info.MainApp;
import info.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;
import util.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class StudentOverviewController {

    private MainApp mainApp;
//    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private Map<Tab,ObservableList<Student>> studentData = new HashMap<>();
    private static int cnt = 1;

    @FXML
    private TabPane tabPane;


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
        showStudentDetail(null);
    }

    public void addStudentTable(){
        Tab newTab = new Tab("Table "+cnt);
        newTab.setClosable(true);
        cnt++;
        TableView studentTable = createTable();


        ObservableList<Student> students = FXCollections.observableArrayList();

        students.add(new Student("sdsa"));
        students.add(new Student("??"));

        studentTable.setItems(students);

        studentTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showStudentDetail((Student) newValue))
        );

        studentData.put(newTab,students);
        newTab.setContent(studentTable);

        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);

    }

    public TableView createTable(){
        TableView table = new TableView();
        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn departCol = new TableColumn("Department");
        TableColumn gpaCol = new TableColumn("GPA");
        TableColumn creditCol = new TableColumn("Credit Earned");

        table.getColumns().addAll(idCol,nameCol,departCol,gpaCol,creditCol);
//        tab.setContent(table);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        departCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("GPA"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditEarned"));

        return table;

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
//        studentTable.setItems(mainApp.getStudentData());
    }

    private void showStudentDetail(Student student){
        if(student!=null){
            idLabel.setText(Integer.toString(student.getID()));
            nameLabel.setText(student.getName());
            birthdayLabel.setText(DateUtil.format(student.getBirthday()));
            genderLabel.setText(student.getGender());
            departmentLabel.setText(student.getDepartment());
            gpaLabel.setText(Double.toString(student.getGPA()));
            creditLabel.setText(Integer.toString(student.getCreditEarned()));
        }else{
            idLabel.setText("");
            nameLabel.setText("");
            birthdayLabel.setText("");
            genderLabel.setText("");
            departmentLabel.setText("");
            gpaLabel.setText("");
            creditLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteStudent(){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        TableView selectedTable = (TableView) selectedTab.getContent();
        int selectIndex = selectedTable.getSelectionModel().getSelectedIndex();
        if(selectIndex>=0){
            selectedTable.getItems().remove(selectIndex);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }



}
