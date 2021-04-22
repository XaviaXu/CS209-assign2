package info.view;

import info.MainApp;
import info.model.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import org.apache.commons.csv.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RootLayoutController {
    private MainApp mainApp;
    private StudentOverviewController controller;

    private static TabPane tabPane;

    final String[] HEADERS = {"ID","Name","Gender","Department","GPA","Credit Earned","Birthday"};
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
    private void handleOpen(){
        //initialize
        Tab newTab = controller.addStudentTable();
        Map<Tab, ObservableList<Student>> studentData = controller.getStudentData();
        ObservableList<Student> data = studentData.get(newTab);


        //open file
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
//            mainApp.loadPersonDataFromFile(file);
            //TODO:READ CSV
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Reader in = null;
            try {
                in = new FileReader(file);
                CSVParser parser = CSVFormat.RFC4180.withHeader(HEADERS)
                        .withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(in);
                List<CSVRecord> records = parser.getRecords();
                for (CSVRecord record : records) {
                    //todo: check data validation
                    Student stu = new Student(record.get("Name"));
                    stu.setID(record.get("ID"));
                    stu.setGender(record.get("Gender"));
                    stu.setDepartment(record.get("Department"));
                    stu.setGPA(record.get("GPA"));
                    stu.setCreditEarned(record.get("Credit Earned"));
                    stu.setBirthday(LocalDate.parse(record.get("Birthday"),fmt));

                    data.add(stu);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    private void handleSave(){
        Map<Tab, ObservableList<Student>> studentData = null;
        Tab selected = null;
        ObservableList<Student> data = null;
        try{
            studentData = controller.getStudentData();
            selected = tabPane.getSelectionModel().getSelectedItem();
            data = studentData.get(selected);
        }catch (Exception e){
            //TODO: ADD DIALOG
        }


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
            mainApp.setStudentFilePath(file);
            //TODO: SAVE DATA TO CSV
            try {
                FileWriter fw = new FileWriter(file);
                CSVPrinter printer = new CSVPrinter(fw, CSVFormat.RFC4180.withHeader(HEADERS).withQuoteMode(QuoteMode.ALL));

                for (Student stu: data) {
//                    System.out.println(stu.getBirthday());
                    printer.printRecord(stu.getID(),stu.getName(),stu.getGender(),stu.getDepartment(),
                            stu.getGPA(),stu.getCreditEarned(),stu.getBirthday());
                }

                fw.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    private void handleSearch(){

    }

}
