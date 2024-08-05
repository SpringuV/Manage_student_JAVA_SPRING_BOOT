package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "score_card")
public class ScoreCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cscore_id")
    private int id;

    // một học sinh có nhiều phiếu điểm
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    @JsonManagedReference
    private Student student;

    @Column(name = "cscore_dexam")
    private Date dayExam;

    @Column(name = "cscore_score")
    private double score;

    // một môn có một phiếu điểm
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "subject_id")
    private Subject subject;



    public ScoreCard(Student student, Date dayExam, double score, Subject subject) {
        this.student = student;
        this.dayExam = dayExam;
        this.score = score;
        this.subject = subject;
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

    public Date getDayExam() {
        return dayExam;
    }

    public void setDayExam(Date dayExam) {
        this.dayExam = dayExam;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "id=" + id +
                ", student=" + student +
                ", dayExam=" + dayExam +
                ", score=" + score +
                ", subject=" + subject +
                '}';
    }
}
