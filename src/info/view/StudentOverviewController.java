package info.view;

import info.MainApp;
import info.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import info.util.DateUtil;
import javafx.stage.Modality;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StudentOverviewController {

    private MainApp mainApp;
    private RootLayoutController rootLayoutController;
    private Map<Tab,ObservableList<Student>> studentData = new HashMap<>();
    private static int cnt = 1;

    private String previousKey = null;
    private int searchCount = 0;


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
        RootLayoutController.setTabPane(tabPane);
        showStudentDetail(null);
    }

    public void setRootLayoutController(RootLayoutController controller){
        this.rootLayoutController = controller;
    }

    public Tab addStudentTable(){
        Tab newTab = new Tab("Table "+cnt);

        newTab.setClosable(true);
        newTab.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"?",
                    new ButtonType("Cancel",ButtonBar.ButtonData.NO),new ButtonType("Confirm",ButtonBar.ButtonData.OK_DONE));
            alert.setTitle("Confirm");
            alert.setHeaderText("Confirm");
            alert.setContentText("Save changes?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                rootLayoutController.handleSave();
            }
        });


        TableView studentTable = createTable();
        studentTable.setId("Table "+cnt);

        cnt++;
        ObservableList<Student> students = FXCollections.observableArrayList();

        studentTable.setItems(students);

        studentTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showStudentDetail((Student) newValue))
        );

        studentData.put(newTab,students);
        newTab.setContent(studentTable);

        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);

        return newTab;
    }

    public TableView createTable(){
        TableView table = new TableView();
        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn genderCol = new TableColumn("Gender");
        TableColumn departCol = new TableColumn("Department");
        TableColumn gpaCol = new TableColumn("GPA");
        TableColumn creditCol = new TableColumn("Credit Earned");
        TableColumn birthdayCol = new TableColumn("Birthday");

        table.getColumns().addAll(idCol,nameCol,genderCol,departCol,gpaCol,creditCol,birthdayCol);
//        tab.setContent(table);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        departCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("GPA"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditEarned"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        return table;

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Map<Tab,ObservableList<Student>> getStudentData(){
        return studentData;
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
    private void handleNewPerson() {
        Student tempStudent = new Student();
        boolean okClicked = mainApp.showStudentEditDialog(tempStudent);
        if (okClicked) {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            studentData.get(selectedTab).add(tempStudent);
        }
    }

    @FXML
    private void handleEditStudent() {
        try {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView selectedTable = (TableView) selectedTab.getContent();
            Student selectedStudent = (Student) selectedTable.getSelectionModel().getSelectedItem();

            boolean okClicked = mainApp.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetail(selectedStudent);
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteStudent(){
        try {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView selectedTable = (TableView) selectedTab.getContent();
            int selectIndex = selectedTable.getSelectionModel().getSelectedIndex();

            selectedTable.getItems().remove(selectIndex);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    public void selectData(String key){
        if(!key.equals(previousKey)){
            previousKey = key;
            searchCount = 0;
        }else{
            searchCount++;
        }
        //search using previousKey and searchCount

        int cnt = searchCount;
        for (Tab tab:studentData.keySet()) {
            ObservableList<Student> data = studentData.get(tab);
            for (int i = 0; i <data.toArray().length ; i++) {
                String stu = data.get(i).toString();
                System.out.println(stu);
                if(stu.contains(key)){
                    cnt--;
                    if(cnt<0){
                        //find correct one
                        tabPane.getSelectionModel().select(tab);
                        TableView selectedTable = (TableView) tab.getContent();
                        selectedTable.getSelectionModel().select(i);
                        break;
                    }
                }
            }

            if(cnt>=0){
                //haven't search
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Not Found");
                alert.setHeaderText("Cannot find relevant student or all results are found out");
                alert.setContentText("Please change keyword.");
                alert.showAndWait();
                searchCount--;
            }

        }




    }

}
