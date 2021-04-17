package info.model;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Student {
    private final StringProperty name;

    private final StringProperty gender;
    private final IntegerProperty ID;
    private final StringProperty department;
    private final DoubleProperty GPA;
    private final IntegerProperty creditEarned;
    private final ObjectProperty<LocalDate> birthday;

    public Student(){
        this(null);
    }
    public Student(String name){
        this.name = new SimpleStringProperty(name);
        //
        this.gender = new SimpleStringProperty("Female");
        this.ID = new SimpleIntegerProperty(0);
        this.department = new SimpleStringProperty("?");
        this.GPA = new SimpleDoubleProperty(4.0);
        this.creditEarned = new SimpleIntegerProperty(10);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public String getGender() {
        return gender.get();
    }

    public String getName() {
        return name.get();
    }

    public double getGPA() {
        return GPA.get();
    }

    public int getCreditEarned() {
        return creditEarned.get();
    }

    public int getID() {
        return ID.get();
    }
    public String getDepartment(){
        return department.get();
    }
    public LocalDate getBirthday(){
        return birthday.get();
    }

    public IntegerProperty idProperty(){return ID;}
    public StringProperty nameProperty(){
        return name;
    }
    public StringProperty departmentProperty(){return department;}
    public DoubleProperty gpaProperty(){return GPA;}
    public IntegerProperty creditProperty(){return creditEarned;}


}
