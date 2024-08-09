package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "transcript")
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transcript_id")
    private int id;

    @Column(name = "name_transcript")
    private String nameTranscript;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "subject_transcript",
            joinColumns = @JoinColumn(name = "transcript_id"), inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "transcript_student",
            joinColumns = @JoinColumn(name = "transcript_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> studentSet;

    @Column(name = "transcript_score")
    private double score;
    @Column(name = "transcript_semester")
    private int semester;

    @Column(name = "school_year")
    private String schoolYear;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "transcript_records", joinColumns = @JoinColumn(name = "transcript_id"), inverseJoinColumns = @JoinColumn(name = "study_record_id"))
    @JsonManagedReference
    private List<StudyRecord> studyRecordList;

    public Transcript() {
    }

    public Transcript(String nameTranscript,Set<Subject> subjectSet, Set<Student> studentSet, double score, int semester,List<StudyRecord> studyRecordList, String schoolYear, School school) {
        this.nameTranscript = nameTranscript;
        this.subjectSet = subjectSet;
        this.studentSet = studentSet;
        this.score = score;
        this.semester = semester;
        this.studyRecordList = studyRecordList;
        this.schoolYear = schoolYear;
        this.school = school;
    }

    public String getNameTranscript() {
        return nameTranscript;
    }

    public void setNameTranscript(String nameTranscript) {
        this.nameTranscript = nameTranscript;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Subject> getSubjectSet() {
        return subjectSet;
    }

    public void setSubjectSet(Set<Subject> subjectSet) {
        this.subjectSet = subjectSet;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
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

    public List<StudyRecord> getStudyRecordList() {
        return studyRecordList;
    }

    public void setStudyRecordList(List<StudyRecord> studyRecordList) {
        this.studyRecordList = studyRecordList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
