package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private int id;

    @Column(name = "school_name")
    private String name;

    @Column(name = "school_address")
    private String address;

    @Column(name = "school_phone")
    private String phone;

    @Column(name = "school_level")
    private String level;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private Set<NoteBook> noteBookSet;

    // một trường có nhiều lớp
    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // đảm bảo không có chu kì lặp giữa các đối tượng
    private List<Classes> classesList;

    // một trường làm việc với nhiều giáo viên
    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Teacher> teacherList;

    // một trường có nhiều học sinh, nhiều học sinh học ở 1 trường
    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Student> studentList;

    // một trường dạy nhiều môn học, nhiều môn học cũng được dạy nhiều ở nhiều trường
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "school_subject", joinColumns = @JoinColumn(name ="school_id"), inverseJoinColumns = @JoinColumn(name ="subject_id"))
    @JsonBackReference
    private List<Subject> subjectList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private List<Transcript> transcriptList;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecordList;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<ScoreCard> scoreCardList;

    public School() {
    }

    public School(String name, String address, String phone, String level, List<Classes> classesList, List<Teacher> teacherList, List<Student> studentList, List<Subject> subjectList, List<Transcript> transcriptList, List<StudyRecord> studyRecordList, List<ScoreCard> scoreCardList) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.level = level;
        this.classesList = classesList;
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.subjectList = subjectList;
        this.transcriptList = transcriptList;
        this.studyRecordList = studyRecordList;
        this.scoreCardList = scoreCardList;
    }

    public School(String name, String address, String phone, String level) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.level = level;
    }

    public List<StudyRecord> getStudyRecordList() {
        return studyRecordList;
    }

    public void setStudyRecordList(List<StudyRecord> studyRecordList) {
        this.studyRecordList = studyRecordList;
    }

    public Set<NoteBook> getNoteBookSet() {
        return noteBookSet;
    }

    public List<ScoreCard> getScoreCardList() {
        return scoreCardList;
    }

    public void setScoreCardList(List<ScoreCard> scoreCardList) {
        this.scoreCardList = scoreCardList;
    }

    public void setNoteBookSet(Set<NoteBook> noteBookSet) {
        this.noteBookSet = noteBookSet;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Transcript> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(List<Transcript> transcriptList) {
        this.transcriptList = transcriptList;
    }
}
