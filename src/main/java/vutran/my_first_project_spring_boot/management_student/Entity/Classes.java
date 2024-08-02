package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "class")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int id;

    @Column(name = "class_name")
    private String name;

    @Column(name = "class_grade")
    private String grade;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name="class_teacher",
        joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonBackReference
    private List<Teacher> teacherList; // Multiple teachers use one classroom

    @OneToMany(mappedBy = "classes", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonBackReference
    private List<Student> studentList;

    @OneToOne(mappedBy = "classes", cascade = CascadeType.ALL)
    private NoteBook noteBook;

    // nhiều lớp chỉ được một trường quản lý
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    public Classes() {
    }

    public Classes(String name, String grade, NoteBook noteBook, School school) {
        this.name = name;
        this.grade = grade;
        this.noteBook = noteBook;
        this.school = school;
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

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", noteBook=" + (noteBook != null ? noteBook.toString() : "null" )+
                ", school=" + (school != null ? school.toString() : "null") +
                '}';
    }
}
