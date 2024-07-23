package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private int id;
    @Column(name = "subject_name")
    private String nameSubject;

    @OneToOne(mappedBy = "subject", cascade = CascadeType.ALL)
    private Transcript transcript;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "teacher_subject",
    joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonBackReference // đảm bảo không có chu kỳ lặp giữa các đối tượng
    private List<Teacher> teacherList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonBackReference
    private List<Student> studentList;

    @OneToOne(mappedBy = "subject", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private ScoreCard scoreCard;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "school_subject", joinColumns = @JoinColumn(name ="subject_id"), inverseJoinColumns = @JoinColumn(name ="school_id"))
    @JsonBackReference
    private List<School> schoolList;


    public Subject() {
    }

    public Subject(String nameSubject, Transcript transcript, List<Teacher> teacherList, List<Student> studentList, ScoreCard scoreCard, List<School> schoolList) {
        this.nameSubject = nameSubject;
        this.transcript = transcript;
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.scoreCard = scoreCard;
        this.schoolList = schoolList;
    }

    public Subject(String nameSubject, Transcript transcript, ScoreCard scoreCard) {
        this.nameSubject = nameSubject;
        this.transcript = transcript;
        this.scoreCard = scoreCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
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

    public ScoreCard getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(ScoreCard scoreCard) {
        this.scoreCard = scoreCard;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", nameSubject='" + nameSubject + '\'' +
                ", transcript=" + transcript +
                ", scoreCard=" + scoreCard +
                '}';
    }
}
