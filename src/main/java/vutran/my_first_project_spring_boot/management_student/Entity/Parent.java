package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;

@Entity
//@DiscriminatorValue("PARENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class Parent extends User{

    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL)
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "p_school_id")
    @JsonBackReference
    private School school;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "p_class_id")
    @JsonBackReference
    private Classes classes;
    public Parent() {
    }

    public Parent(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Parent(String firstName, String lastName, String address, String phoneNumber, String identity, String username, String password, Boolean enabled, String email, String position, Blob avatar, Collection<Authority> authority, Student student, School school, Classes classes) {
        super(firstName, lastName, address, phoneNumber, identity, username, password, enabled, email, position, avatar, authority);
        this.student = student;
        this.school = school;
        this.classes = classes;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public Classes getClasses() {
        return classes;
    }

    @Override
    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "student=" + student.getId() +
                ", school=" + school.getName() +
                '}';
    }
}
