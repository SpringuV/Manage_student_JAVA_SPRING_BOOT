package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;
import java.util.Set;

@Entity
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference // đảm bảo không có chu kỳ lặp giữa các đối tượng
    private Set<Student> studentList;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonManagedReference
    @JoinColumn(name = "class_id")
    private Classes classes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JsonManagedReference
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Một giáo viên có thể làm việc ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    @OneToMany (mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<NoteBookDetail> noteBookDetailList;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Teacher(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName, Set<Student> studentList, Classes classes,Subject subject, List<NoteBookDetail> noteBookDetailList, School school) {
        super(avatar, position, email, enabled, password, username, identity, phoneNumber, address, lastName, firstName);
        this.studentList = studentList;
        this.classes = classes;
        this.subject = subject;
        this.noteBookDetailList = noteBookDetailList;
        this.school = school;
    }

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }
    
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<NoteBookDetail> getNoteBookDetailList() {
        return noteBookDetailList;
    }

    public void setNoteBookDetailList(List<NoteBookDetail> noteBookDetailList) {
        this.noteBookDetailList = noteBookDetailList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }
}
