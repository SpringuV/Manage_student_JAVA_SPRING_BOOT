package vutran.my_first_project_spring_boot.management_student.Entity;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;

@Entity
public class Parent extends User{

    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL)
    private Student student;

    public Parent() {
    }

    public Parent(String firstName, String lastName, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, address, phoneNumber, username, email, position, avatar);
    }

    public Parent(String firstName, String lastName, String address, String phoneNumber, String username, String password, Boolean enabled, String email, String position, Blob avatar) {
        super(firstName, lastName, address, phoneNumber, username, password, enabled, email, position, avatar);
    }

    public Parent(String firstName, String lastName, String address, String phoneNumber, String username, String password, Boolean enabled, String email, String position, Blob avatar, Student student) {
        super(firstName, lastName, address, phoneNumber, username, password, enabled, email, position, avatar);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
