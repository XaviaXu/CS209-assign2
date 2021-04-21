package info.view;

import info.model.Student;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;


public class StudentEditDialogController {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox genderField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField gpaField;
    @FXML
    private TextField creditField;
    @FXML
    private DatePicker birthdayField;

    private Stage dialogStage;
    private Student student;
    private boolean okClicked = false;
    private ObservableList<String> gender = FXCollections.observableArrayList("MALE","FEMALE");

    @FXML
    private void initialize() {
//        genderField.getItems().addAll(gender);
        genderField.getItems().addAll("MALE","FEMALE");
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStudent(Student student){
        this.student = student;

        idField.setText(Integer.toString(student.getID()));
        nameField.setText(student.getName());
        departmentField.setText(student.getDepartment());
        gpaField.setText(Double.toString(student.getGPA()));
        creditField.setText(Integer.toString(student.getCreditEarned()));
        if(student.getGender().equals("MALE")){
            genderField.getSelectionModel().select(0);
        }else{
            genderField.getSelectionModel().select(1);
        }
        birthdayField.setValue(student.getBirthday());


    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOK(){
        if(isInputValid()){
            int index = genderField.getSelectionModel().selectedIndexProperty().intValue();
            String gender = index==1?"FEMALE":"MALE";
            LocalDate birthday = birthdayField.getValue();

            student.setID(idField.getText());
            student.setName(nameField.getText());
            student.setGender(gender);
            student.setDepartment(departmentField.getText());
            student.setCreditEarned(creditField.getText());
            student.setGPA(gpaField.getText());
            student.setBirthday(birthday);

            okClicked = true;
            dialogStage.close();

        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";





        return true;
    }






}
