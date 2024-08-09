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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "sr_student_id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "school_year")
    private String schoolYear;

    // một học bạ có nhiều bảng điểm;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "transcript_records", joinColumns = @JoinColumn(name = "study_record_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id"))
    @JsonBackReference
    private List<Transcript> transcriptList;

    @Column(name = "sr_conduct") // hanh kiem
    private String resultConduct;
    @Column(name = "sr_comment_teacher")
    private String commentOfTeacher;
    public StudyRecord() {
    }

    public StudyRecord(Student student, String resultConduct, String commentOfTeacher, String schoolYear) {
        this.student = student;
        this.resultConduct = resultConduct;
        this.commentOfTeacher = commentOfTeacher;
        this.schoolYear = schoolYear;
    }

    public StudyRecord(Student student, List<Transcript> transcriptList, String resultConduct, String commentOfTeacher,  School school, String schoolYear) {
        this.student = student;
        this.transcriptList = transcriptList;
        this.resultConduct = resultConduct;
        this.commentOfTeacher = commentOfTeacher;
        this.school = school;
        this.schoolYear = schoolYear;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
