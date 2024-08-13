package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends User{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    // Một học sinh học nhiều môn học, một môn học được học bởi nhiều học sinh
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjectList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "transcript_student",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id"))
    private Set<Transcript> transcriptSet;

    // mỗi học sinh có 1 thẻ học sinh
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentCard studentCard;

    // một học sinh có nhiều phiếu điểm
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private Set<ScoreCard> scoreCardList;

    // một học sinh có 1 học bạ
    @OneToMany (mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecordList;

    // Nhiều học sinh chỉ học ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    private School school;

    public Student() {
    }

    public Student(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Student(String firstName, String lastName, String address, String phoneNumber, String identity, String username, String password, Boolean enabled, String email, String position, Blob avatar, Collection<Authority> authority) {
        super(firstName, lastName, address, phoneNumber, identity, username, password, enabled, email, position, avatar, authority);
    }

    public Student(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName, Teacher teacher, Classes classes, Parent parent, Set<Subject> subjectList, Set<Transcript> transcriptSet, StudentCard studentCard, Set<ScoreCard> scoreCardList, List<StudyRecord> studyRecordList, School school) {
        super(avatar, position, email, enabled, password, username, identity, phoneNumber, address, lastName, firstName);
        this.teacher = teacher;
        this.classes = classes;
        this.parent = parent;
        this.subjectList = subjectList;
        this.transcriptSet = transcriptSet;
        this.studentCard = studentCard;
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

    public StudentCard getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(StudentCard studentCard) {
        this.studentCard = studentCard;
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
                "teacher=" + teacher.getFirstName() +
                ", classes=" + classes.getName() +
                ", parent=" + parent.getFirstName() +
                ", studentCard=" + studentCard.getId() +
                ", school=" + school.getName() +
                '}';
    }
}
