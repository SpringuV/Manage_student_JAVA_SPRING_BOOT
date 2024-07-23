package vutran.my_first_project_spring_boot.management_student.Entity;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "student_card")
public class StudentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_student_id")
    private Student student;

    @Lob
    @Column(name = "cart_avatar")
    private Blob avatar;

    public StudentCard() {
    }

    public StudentCard(Student student, Blob avatar) {
        this.student = student;
        this.avatar = avatar;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentCard{" +
                "id=" + id +
                ", student=" + student +
                ", avatar=" + avatar +
                '}';
    }
}
