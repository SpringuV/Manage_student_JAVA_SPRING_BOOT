package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference // đảm bảo không có chu kỳ lặp giữa các đối tượng
    private Set<Student> studentList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "teacher_classes",
        joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    @JsonManagedReference
    private Set<Classes> classesList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonManagedReference
    private Set<Subject> subjectList;

    // Một giáo viên có thể làm việc ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    @OneToMany (mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NoteBookDetail> noteBookDetailList;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        super(firstName, lastName, identity, address, phoneNumber, username, email, position, avatar);
    }

    public Teacher(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName, Set<Student> studentList, Set<Classes> classesList, Set<Subject> subjectList, List<NoteBookDetail> noteBookDetailList, School school) {
        super(avatar, position, email, enabled, password, username, identity, phoneNumber, address, lastName, firstName);
        this.studentList = studentList;
        this.classesList = classesList;
        this.subjectList = subjectList;
        this.noteBookDetailList = noteBookDetailList;
        this.school = school;
    }

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }

    public Set<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(Set<Classes> classesList) {
        this.classesList = classesList;
    }

    public Set<Subject> getSubjectList() {
        return subjectList;
    }

    public List<NoteBookDetail> getNoteBookDetailList() {
        return noteBookDetailList;
    }

    public void setNoteBookDetailList(List<NoteBookDetail> noteBookDetailList) {
        this.noteBookDetailList = noteBookDetailList;
    }

    public void setSubjectList(Set<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
