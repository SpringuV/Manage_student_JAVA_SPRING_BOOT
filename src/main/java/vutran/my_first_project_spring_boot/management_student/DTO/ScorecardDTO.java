package vutran.my_first_project_spring_boot.management_student.DTO;

import java.sql.Date;

public class ScorecardDTO {
    private int id;
    private String nameExam, schoolYear;
    private double score;
    private int semester;
    private Date dayExam;
    private int subject_id;
    private String nameSubject;

    public ScorecardDTO() {
    }

    public ScorecardDTO(int id, String nameExam, String schoolYear, double score, int semester, Date dayExam, int subject_id, String nameSubject) {
        this.id = id;
        this.nameExam = nameExam;
        this.schoolYear = schoolYear;
        this.score = score;
        this.semester = semester;
        this.dayExam = dayExam;
        this.subject_id = subject_id;
        this.nameSubject = nameSubject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameExam() {
        return nameExam;
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Date getDayExam() {
        return dayExam;
    }

    public void setDayExam(Date dayExam) {
        this.dayExam = dayExam;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }
}
