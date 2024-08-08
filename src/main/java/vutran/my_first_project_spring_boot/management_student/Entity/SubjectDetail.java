//package vutran.my_first_project_spring_boot.management_student.Entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Table(name = "detail_subject")
//public class SubjectDetail {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_detail")
//    private int id;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "sub_detail_transcript",
//            joinColumns = @JoinColumn(name = "sub_detail_id"), inverseJoinColumns = @JoinColumn(name = "transcript_id")
//    )
//    private Set<Transcript> transcriptList;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
//    @JoinTable(name = "student_sub_detail",
//            joinColumns = @JoinColumn(name = "sub_detail_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
//    @JsonBackReference
//    private Set<Student> studentList;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
//    @JoinTable(name = "sub_detail_score_card", joinColumns = @JoinColumn(name = "sub_detail_id"),
//        inverseJoinColumns = @JoinColumn(name = "score_card_id")
//    )
//    private Set<ScoreCard> scoreCard;
//
//    // một môn có nhiều tiết dạy trong notebook
//    @OneToMany(mappedBy = "subjectDetail", cascade = CascadeType.ALL)
//    private Set<NoteBookDetail> noteBookDetailList;
//
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "detail_subject_id")
//    private Subject subject;
//
//    public SubjectDetail() {
//    }
//
//    public SubjectDetail(Set<Transcript> transcriptList, Set<Student> studentList, Set<ScoreCard> scoreCard, Set<NoteBookDetail> noteBookDetailList, Subject subject) {
//        this.transcriptList = transcriptList;
//        this.studentList = studentList;
//        this.scoreCard = scoreCard;
//        this.noteBookDetailList = noteBookDetailList;
//        this.subject = subject;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Set<Transcript> getTranscriptList() {
//        return transcriptList;
//    }
//
//    public void setTranscriptList(Set<Transcript> transcriptList) {
//        this.transcriptList = transcriptList;
//    }
//
//    public Set<Student> getStudentList() {
//        return studentList;
//    }
//
//    public void setStudentList(Set<Student> studentList) {
//        this.studentList = studentList;
//    }
//
//    public Set<ScoreCard> getScoreCard() {
//        return scoreCard;
//    }
//
//    public void setScoreCard(Set<ScoreCard> scoreCard) {
//        this.scoreCard = scoreCard;
//    }
//
//    public Set<NoteBookDetail> getNoteBookDetailList() {
//        return noteBookDetailList;
//    }
//
//    public void setNoteBookDetailList(Set<NoteBookDetail> noteBookDetailList) {
//        this.noteBookDetailList = noteBookDetailList;
//    }
//
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//}
