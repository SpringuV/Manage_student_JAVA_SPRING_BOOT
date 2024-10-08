package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
//@DiscriminatorValue("STUDENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class Student extends User{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Classes classes;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Parent parent;

    // Một học sinh học nhiều môn học, một môn học được học bởi nhiều học sinh
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonManagedReference
    private Set<Subject> subjectList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "transcript_student",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id"))
    @JsonManagedReference
    private Set<Transcript> transcriptSet;

    // một học sinh có nhiều phiếu điểm
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Set<ScoreCard> scoreCardList;

    // một học sinh có nhiều học bạ qua từng năm
    @OneToMany (fetch = FetchType.LAZY,mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StudyRecord> studyRecordList;

    // Nhiều học sinh chỉ học ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;

    public Student() {
    }

    public Student(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Student(String firstName, String lastName, String address, String phoneNumber, String identity, String username, String password, Boolean enabled, String email, String position, Blob avatar, Collection<Authority> authority) {
        super(firstName, lastName, address, phoneNumber, identity, username, password, enabled, email, position, avatar, authority);
    }

    public Student(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName, Teacher teacher, Classes classes, Parent parent, Set<Subject> subjectList, Set<Transcript> transcriptSet, Set<ScoreCard> scoreCardList, List<StudyRecord> studyRecordList, School school) {
        super(avatar, position, email, enabled, password, username, identity, phoneNumber, address, lastName, firstName);
        this.teacher = teacher;
        this.classes = classes;
        this.parent = parent;
        this.subjectList = subjectList;
        this.transcriptSet = transcriptSet;
        this.scoreCardList = scoreCardList;
        this.studyRecordList = studyRecordList;
        this.school = school;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Set<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(Set<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Set<Transcript> getTranscriptSet() {
        return transcriptSet;
    }

    public void setTranscriptSet(Set<Transcript> transcriptSet) {
        this.transcriptSet = transcriptSet;
    }

    public Set<ScoreCard> getScoreCardList() {
        return scoreCardList;
    }

    public void setScoreCardList(Set<ScoreCard> scoreCardList) {
        this.scoreCardList = scoreCardList;
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

    @Override
    public String toString() {
        return "Student{" +
                "teacher=" + (teacher != null ? teacher.getId() : "No Teacher") +
                ", classes=" + classes.getName() +
                ", parent=" + parent.getId() +
                ", subjectList=" + subjectList.size() +
                ", transcriptSet=" + transcriptSet.size() +
                ", scoreCardList=" + scoreCardList.size() +
                ", studyRecordList=" + studyRecordList.size() +
                ", school=" + school.getName() +
                '}';
    }
}
