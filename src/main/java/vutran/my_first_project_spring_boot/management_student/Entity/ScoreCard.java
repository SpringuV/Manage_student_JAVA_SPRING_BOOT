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

    @Column(name = "cs_name_exam")
    private String nameExam;

    // một học sinh có nhiều phiếu điểm
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sc_school_id")
    @JsonBackReference
    private School school;

    @Column(name = "cscore_dexam")
    private Date dayExam;

    @Column(name = "cscore_score")
    private double score;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "cs_subject_id")
    @JsonBackReference
    private Subject subject;

    @Column(name = "cs_school_year")
    private String schoolYear;

    public ScoreCard() {
    }

    public ScoreCard(String nameExam, Student student, Date dayExam, double score, Subject subject, School school, String schoolYear) {
        this.student = student;
        this.dayExam = dayExam;
        this.score = score;
        this.subject = subject;
        this.school = school;
        this.schoolYear = schoolYear;
    }

    public String getNameExam() {
        return nameExam;
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
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
                ", nameExam='" + nameExam + '\'' +
                ", student=" + student +
                ", school=" + school +
                ", dayExam=" + dayExam +
                ", score=" + score +
                ", subject=" + subject +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
