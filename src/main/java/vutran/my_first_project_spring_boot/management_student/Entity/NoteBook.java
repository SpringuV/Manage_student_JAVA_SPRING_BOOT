package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "note_book")
public class NoteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    @OneToMany(mappedBy = "noteBook", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<NoteBookDetail> noteBookDetailList;

    // một sổ đầu bài tương ứng 1 lớp
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_class_id")
    private Classes classes;

    public NoteBook() {
    }

    public NoteBook(School school, List<NoteBookDetail> noteBookDetail, Classes classes) {
        this.school = school;
        this.noteBookDetailList = noteBookDetail;
        this.classes = classes;
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

    public List<NoteBookDetail> getNoteBookDetail() {
        return noteBookDetailList;
    }

    public void setNoteBookDetail(List<NoteBookDetail> noteBookDetail) {
        this.noteBookDetailList = noteBookDetail;
    }
}
