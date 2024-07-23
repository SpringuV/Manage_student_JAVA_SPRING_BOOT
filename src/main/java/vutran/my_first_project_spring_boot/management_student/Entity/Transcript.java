package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transcript")
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transcript_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "transcript_score")
    private double score;
    @Column(name = "transcript_semester")
    private int  semester;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "stu_record_id")
    @JsonManagedReference
    private StudyRecord studyRecord;

    public Transcript() {
    }

    public Transcript(Subject subject, Student student, double score, int semester, StudyRecord studyRecord) {
        this.subject = subject;
        this.student = student;
        this.score = score;
        this.semester = semester;
        this.studyRecord = studyRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public StudyRecord getStudyRecord() {
        return studyRecord;
    }

    public void setStudyRecord(StudyRecord studyRecord) {
        this.studyRecord = studyRecord;
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "id=" + id +
                ", subject=" + subject +
                ", student=" + student +
                ", score=" + score +
                ", semester=" + semester +
                ", studyRecord=" + studyRecord +
                '}';
    }
}
