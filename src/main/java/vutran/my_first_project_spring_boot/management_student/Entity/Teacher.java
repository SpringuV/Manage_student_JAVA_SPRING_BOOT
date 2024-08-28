package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;
import java.util.Set;

@Entity
//@DiscriminatorValue("TEACHER")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade ={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference // đảm bảo không có chu kỳ lặp giữa các đối tượng
    private Set<Student> studentList;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonBackReference
    @JoinColumn(name = "class_id")
    private Classes classes;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JsonBackReference
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Một giáo viên có thể làm việc ở một trường
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;

    @OneToMany (mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
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

    @Override
    public String toString() {
        return "Teacher{" +
                "studentList=" + studentList.size() +
                ", classes=" + classes.getName() +
                ", subject=" + subject.getNameSubject() +
                ", school=" + school.getName() +
                ", noteBookDetailList=" + noteBookDetailList.size() +
                '}';
    }
}
