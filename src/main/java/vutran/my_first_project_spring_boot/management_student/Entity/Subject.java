package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

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
    private Set<Teacher> teacherList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonBackReference
    private Set<Student> studentList;

    @OneToMany
    private List<NoteBookDetail> noteBookDetailList;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<ScoreCard> scoreCard;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "school_subject", joinColumns = @JoinColumn(name ="subject_id"), inverseJoinColumns = @JoinColumn(name ="school_id"))
    @JsonBackReference
    private List<School> schoolList;


    public Subject() {
    }

    public Subject(String nameSubject, Transcript transcript, Set<Teacher> teacherList, Set<Student> studentList, List<ScoreCard> scoreCard, List<School> schoolList) {
        this.nameSubject = nameSubject;
        this.transcript = transcript;
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.scoreCard = scoreCard;
        this.schoolList = schoolList;
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

    public Set<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(Set<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }

    public List<ScoreCard> getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(List<ScoreCard> scoreCard) {
        this.scoreCard = scoreCard;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }
}
