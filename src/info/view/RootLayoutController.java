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
import java.util.prefs.Preferences;

public class RootLayoutController {
    private MainApp mainApp;
    private StudentOverviewController controller;

    private static TabPane tabPane;
    private static int index;

    final String[] HEADERS = {"ID","Name","Gender","Department","GPA","Credit Earned","Birthday"};

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

        Tab newTab = controller.addStudentTable();
        String name = newTab.getText();
        setStudentFilePath(name,null);

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
            setStudentFilePath(newTab.getText(), file);
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
    public void handleSave(){
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

        File studentFile = getStudentFilePath(selected.getText());
        if(studentFile!=null){
            saveStudentDataToFile(data,studentFile);
        }else{
            handleSaveAs();
        }
    }


    @FXML
    private void handleSaveAs(){
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
            setStudentFilePath(selected.getText(),file);
            saveStudentDataToFile(data, file);
        }

    }

    private void saveStudentDataToFile(ObservableList<Student> data, File file) {

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


    @FXML
    private void handleSearch(){

    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setStudentFilePath(String tabName,File file) {
        Preferences prefs = Preferences.userNodeForPackage(RootLayoutController.class);
        if (file != null) {
            prefs.put(tabName, file.getPath());
        } else {
            prefs.remove(tabName);
        }
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getStudentFilePath(String tabName) {
        Preferences prefs = Preferences.userNodeForPackage(RootLayoutController.class);
        String filePath = prefs.get(tabName, null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

}
