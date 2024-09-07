package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "subjects")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "subject_name")
    private String nameSubject;

    @Column(name = "subject_level")
    private String sub_level;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "school_subject", joinColumns = @JoinColumn(name ="subject_id"), inverseJoinColumns = @JoinColumn(name ="school_id"))
    @JsonBackReference
    private Set<School> schoolList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    @JoinTable(name = "subject_transcript",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id")
    )
    private Set<Transcript> transcriptList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonBackReference
    private Set<Student> studentList;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Set<ScoreCard> scoreCard;

    // một môn có nhiều tiết dạy trong notebook
    @OneToMany( fetch = FetchType.LAZY,mappedBy = "subject", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Set<NoteBookDetail> noteBookDetailList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subject")
    @JsonManagedReference
    private Set<Teacher> teacherSet;
    
    public Subject() {
    }

    public Subject(String nameSubject, Set<School> schoolList, Set<Transcript> transcriptList, Set<Student> studentList, Set<ScoreCard> scoreCard, Set<NoteBookDetail> noteBookDetailList, Set<Teacher> teacherSet, String sub_level) {
        this.nameSubject = nameSubject;
        this.schoolList = schoolList;
        this.transcriptList = transcriptList;
        this.studentList = studentList;
        this.scoreCard = scoreCard;
        this.noteBookDetailList = noteBookDetailList;
        this.teacherSet = teacherSet;
        this.sub_level = sub_level;
    }

    public String getSub_level() {
        return sub_level;
    }

    public void setSub_level(String sub_level) {
        this.sub_level = sub_level;
    }

    public Set<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(Set<School> schoolList) {
        this.schoolList = schoolList;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
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

    public Set<Transcript> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(Set<Transcript> transcriptList) {
        this.transcriptList = transcriptList;
    }

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }

    public Set<ScoreCard> getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(Set<ScoreCard> scoreCard) {
        this.scoreCard = scoreCard;
    }

    public Set<NoteBookDetail> getNoteBookDetailList() {
        return noteBookDetailList;
    }

    public void setNoteBookDetailList(Set<NoteBookDetail> noteBookDetailList) {
        this.noteBookDetailList = noteBookDetailList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", nameSubject='" + nameSubject + '\'' +
                ", schoolList=" + schoolList.size() +
                ", transcriptList=" + transcriptList.size() +
                ", studentList=" + studentList.size() +
                ", scoreCard=" + scoreCard.size() +
                ", noteBookDetailList=" + noteBookDetailList.size() +
                ", teacherSet=" + teacherSet.size() +
                '}';
    }
}
