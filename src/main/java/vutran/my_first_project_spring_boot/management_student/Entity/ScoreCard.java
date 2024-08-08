package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "sub_score_card", joinColumns = @JoinColumn(name = "score_card_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_id")
    )
    private Set<Subject> subjectSet;

    public ScoreCard(Student student, Date dayExam, double score, Set<Subject> subjectSet) {
        this.student = student;
        this.dayExam = dayExam;
        this.score = score;
        this.subjectSet = subjectSet;
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

    public Set<Subject> getSubjectSet() {
        return subjectSet;
    }

    public void setSubjectSet(Set<Subject> subjectSet) {
        this.subjectSet = subjectSet;
    }
}
