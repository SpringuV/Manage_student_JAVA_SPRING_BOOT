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
    @Column(name = "id")
    private int id;
    @Column(name = "subject_name")
    private String nameSubject;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "school_subject", joinColumns = @JoinColumn(name ="subject_id"), inverseJoinColumns = @JoinColumn(name ="school_id"))
    @JsonBackReference
    private Set<School> schoolList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "subject_transcript",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id")
    )
    private Set<Transcript> transcriptList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonBackReference
    private Set<Student> studentList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "subject_score_card", joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "score_card_id")
    )
    private Set<ScoreCard> scoreCard;

    // một môn có nhiều tiết dạy trong notebook
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<NoteBookDetail> noteBookDetailList;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subject")
    private Set<Teacher> teacherSet;

    public Subject() {
    }

    public Subject(String nameSubject, Set<School> schoolList, Set<Teacher> teacherSet) {
        this.nameSubject = nameSubject;
        this.schoolList = schoolList;
        this.teacherSet = teacherSet;
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
}
