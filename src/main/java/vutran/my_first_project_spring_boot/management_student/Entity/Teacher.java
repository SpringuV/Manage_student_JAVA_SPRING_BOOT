package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

@Entity
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference // đảm bảo không có chu kỳ lặp giữa các đối tượng
    private List<Student> studentList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "teacher_classes",
        joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    @JsonManagedReference
    private List<Classes> classesList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonManagedReference
    private List<Subject> subjectList;

    // một giáo viên kí nhiều sổ đầu bài
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name = "teacher_notebook",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "notebook_id"))
    @JsonManagedReference
    private List<NoteBook> noteBookList;

    // Một giáo viên có thể làm việc ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String address, String phoneNumber, String username, String password, Boolean enabled, String email, Blob avatar) {
        super(firstName, lastName, address, phoneNumber, username, password, enabled, email, avatar);
    }

    public Teacher(String firstName, String lastName, String address, String phoneNumber, String username, String password, Boolean enabled, String email, Blob avatar, School school, List<NoteBook> noteBookList, List<Subject> subjectList, List<Classes> classesList, List<Student> studentList) {
        super(firstName, lastName, address, phoneNumber, username, password, enabled, email, avatar);
        this.school = school;
        this.noteBookList = noteBookList;
        this.subjectList = subjectList;
        this.classesList = classesList;
        this.studentList = studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<NoteBook> getNoteBookList() {
        return noteBookList;
    }

    public void setNoteBookList(List<NoteBook> noteBookList) {
        this.noteBookList = noteBookList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
