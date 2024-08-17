package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "notebook_detail")
public class NoteBookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_detail_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    @JoinColumn( name = "notebook_id")
    private NoteBook noteBook;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "note_content_lec")
    private String contentLecture;

    @Column(name = "note_teachday")
    private Date teachingDay;

    @Column(name = "note_time")
    private String time;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private Subject subject;

    @Column(name = "note_teachcomment")
    private String teacherComment;

    public NoteBookDetail() {
    }

    public NoteBookDetail(NoteBook noteBook, Teacher teacher, String contentLecture, Date teachingDay, String teacherComment,String time ,Subject subject) {
        this.noteBook = noteBook;
        this.teacher = teacher;
        this.time = time;
        this.contentLecture = contentLecture;
        this.teachingDay = teachingDay;
        this.teacherComment = teacherComment;
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
        return "NoteBookDetail{" +
                "id=" + id +
                ", noteBook=" + noteBook.getId() +
                ", teacher=" + teacher +
                ", contentLecture='" + contentLecture + '\'' +
                ", teachingDay=" + teachingDay +
                ", time='" + time + '\'' +
                ", subject=" + subject.getNameSubject() +
                ", teacherComment='" + teacherComment + '\'' +
                '}';
    }
}
