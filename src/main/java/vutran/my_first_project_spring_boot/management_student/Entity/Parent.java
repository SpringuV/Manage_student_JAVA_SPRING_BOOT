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

    public Parent(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Parent(String firstName, String lastName, String address, String phoneNumber, String identity, String username, String password, Boolean enabled, String email, String position, Blob avatar, Collection<Authority> authority) {
        super(firstName, lastName, address, phoneNumber, identity, username, password, enabled, email, position, avatar, authority);
    }

    public Parent(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName, Student student) {
        super(avatar, position, email, enabled, password, username, identity, phoneNumber, address, lastName, firstName);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
