package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "class")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int id;

    @Column(name = "class_name")
    private String name;

    @Column(name = "class_grade")
    private String grade;

    @OneToMany(mappedBy = "classes",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Teacher> teacherList;

    @OneToMany(mappedBy = "classes", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Student> studentList;

    @OneToOne(mappedBy = "classes", cascade = CascadeType.ALL)
    private NoteBook noteBook;

    // nhiều lớp chỉ được một trường quản lý
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "c_school_id")
    @JsonBackReference
    private School school;


    @OneToMany(mappedBy = "classes", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Parent> parentList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "transcript_class", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id"))
    @JsonManagedReference
    private List<Transcript> transcriptList;


    public Classes() {
    }

    public Classes(String name, String grade, List<Teacher> teacherList, List<Student> studentList, NoteBook noteBook, School school, List<Parent> parentList, List<Transcript> transcriptList) {
        this.name = name;
        this.grade = grade;
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.noteBook = noteBook;
        this.school = school;
        this.parentList = parentList;
        this.transcriptList = transcriptList;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public List<Transcript> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(List<Transcript> transcriptList) {
        this.transcriptList = transcriptList;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", teacherList=" + teacherList.size() +
                ", studentList=" + studentList.size() +
                ", parentList=" + parentList.size() +
                ", transcriptList=" + transcriptList.size() +
                ", noteBook=" + noteBook +
                ", school=" + school +
                '}';
    }
}
