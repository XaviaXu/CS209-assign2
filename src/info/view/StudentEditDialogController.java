package info.view;

import info.model.Student;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        if(idField.getText()==null||idField.getText().length()==0){
            errorMessage+="No valid ID found!\n";
        }else{
            int temp = 0;
            try{
                temp = Integer.parseInt(idField.getText());
                if(temp<0){
                    errorMessage+="ID pattern invalid !\n";
                }
            }catch (NumberFormatException e){
                errorMessage+="ID pattern invalid !\n";
            }

        }
        if(nameField.getText()==null||nameField.getText().length()==0){
            errorMessage+="No valid name found!\n";
        }
        if(departmentField.getText()==null||departmentField.getText().length()==0){
            errorMessage+="No valid department found!\n";
        }
        if(gpaField.getText()==null||gpaField.getText().length()==0){
            errorMessage+="No valid GPA found!\n";
        }else{
            double gpa;
            try{
                gpa = Double.parseDouble(gpaField.getText());
                if(gpa<0||gpa>4){
                    errorMessage+="GPA should belong to the interval [0.0,4.0]!\n";
                }
            }catch (Exception e){
                errorMessage+="No valid GPA (must be a double value)!\n";
            }
        }
        if(creditField.getText()==null||creditField.getText().length()==0){
            errorMessage+="No valid credit earned found!\n";
        }else{
            try{
              int credit = Integer.parseInt(creditField.getText());
              if(credit<0){
                  errorMessage+="Credit Earned should greater than or equal 0!\n";
              }
            }catch (Exception e){
                errorMessage+="Credit Earned should be an integer!\n";
            }
        }
        if(errorMessage.length()!=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid info");
            alert.setHeaderText("Please input valid info.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
