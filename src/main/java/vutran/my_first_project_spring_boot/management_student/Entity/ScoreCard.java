package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Date;
@Entity
@Table(name = "score_card")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "cs_transcript_id")
    @JsonBackReference
    private Transcript transcript;

    @Column(name = "cs_school_year")
    private String schoolYear;

    public ScoreCard() {
    }

    public ScoreCard(String nameExam, Student student, Date dayExam, double score, Subject subject, School school, String schoolYear, Transcript transcript) {
        this.student = student;
        this.dayExam = dayExam;
        this.score = score;
        this.subject = subject;
        this.school = school;
        this.schoolYear = schoolYear;
        this.transcript = transcript;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
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
                ", student=" + student.getId() +
                ", school=" + school.getName() +
                ", dayExam=" + dayExam +
                ", score=" + score +
                ", subject=" + subject +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
