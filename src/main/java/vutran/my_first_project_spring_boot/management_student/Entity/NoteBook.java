package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "note_book")
public class NoteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // một sổ đầu bài tương ứng 1 lớp
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_class_id")
    private Classes classes;

    // một sổ có nhiều giáo viên kí
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_notebook",
            joinColumns = @JoinColumn(name = "notebook_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonManagedReference
    private Set<Teacher> teacherList;

    @Column(name = "note_content_lec")
    private String contentLecture;

    @Column(name = "note_teachday")
    private Date teachingDay;

    @Column(name = "note_teachcomment")
    private String teacherComment;

    public NoteBook() {
    }

    public NoteBook(Classes classes, String contentLecture, Date teachingDay, String teacherComment) {
        this.classes = classes;
        this.contentLecture = contentLecture;
        this.teachingDay = teachingDay;
        this.teacherComment = teacherComment;
    }

    public NoteBook(Classes classes, Set<Teacher> teacherList, String contentLecture, Date teachingDay, String teacherComment) {
        this.classes = classes;
        this.teacherList = teacherList;
        this.contentLecture = contentLecture;
        this.teachingDay = teachingDay;
        this.teacherComment = teacherComment;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Set<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(Set<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public String getContentLecture() {
        return contentLecture;
    }

    public void setContentLecture(String contentLecture) {
        this.contentLecture = contentLecture;
    }

    public Date getTeachingDay() {
        return teachingDay;
    }

    public void setTeachingDay(Date teachingDay) {
        this.teachingDay = teachingDay;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    @Override
    public String toString() {
        return "NoteBook{" +
                "id=" + id +
                ", classes=" + (classes != null ? classes.toString() : "null") +
                ", teacherList=" + (teacherList != null ? teacherList.toString() : "null") +
                ", school=" + (school != null ? school.toString() : "null") +
                ", contentLecture='" + contentLecture + '\'' +
                ", teachingDay=" + teachingDay +
                ", teacherComment='" + teacherComment + '\'' +
                '}';
    }
}
