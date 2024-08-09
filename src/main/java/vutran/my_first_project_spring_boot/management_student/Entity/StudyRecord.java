package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "study_record")
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sr_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sr_student_id")
    private Student student;

    // một học bạ có nhiều bảng điểm;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "transcript_records", joinColumns = @JoinColumn(name = "study_record_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id"))
    @JsonBackReference
    private List<Transcript> transcriptList;

    @Column(name = "sr_conduct")
    private String resultConduct;
    @Column(name = "sr_comment_teacher")
    private String commentOfTeacher;
    @Column(name = "sr_comment_parent")
    private String commentOfParent;

    public StudyRecord() {
    }

    public StudyRecord(Student student, String resultConduct, String commentOfTeacher, String commentOfParent) {
        this.student = student;
        this.resultConduct = resultConduct;
        this.commentOfTeacher = commentOfTeacher;
        this.commentOfParent = commentOfParent;
    }

    public StudyRecord(Student student, List<Transcript> transcriptList, String resultConduct, String commentOfTeacher, String commentOfParent) {
        this.student = student;
        this.transcriptList = transcriptList;
        this.resultConduct = resultConduct;
        this.commentOfTeacher = commentOfTeacher;
        this.commentOfParent = commentOfParent;
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

    public List<Transcript> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(List<Transcript> transcriptList) {
        this.transcriptList = transcriptList;
    }

    public String getResultConduct() {
        return resultConduct;
    }

    public void setResultConduct(String resultConduct) {
        this.resultConduct = resultConduct;
    }

    public String getCommentOfTeacher() {
        return commentOfTeacher;
    }

    public void setCommentOfTeacher(String commentOfTeacher) {
        this.commentOfTeacher = commentOfTeacher;
    }

    public String getCommentOfParent() {
        return commentOfParent;
    }

    public void setCommentOfParent(String commentOfParent) {
        this.commentOfParent = commentOfParent;
    }

    @Override
    public String toString() {
        return "StudyRecord{" +
                "id=" + id +
                ", student=" + student +
                ", resultConduct='" + resultConduct + '\'' +
                ", commentOfTeacher='" + commentOfTeacher + '\'' +
                ", commentOfParent='" + commentOfParent + '\'' +
                '}';
    }
}
