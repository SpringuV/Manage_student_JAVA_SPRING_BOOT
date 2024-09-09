package vutran.my_first_project_spring_boot.management_student.DTO;

public class TranscriptDTO {
    private int id, semester;
    private String nameTranscript, schoolYear;

    public TranscriptDTO() {
    }

    public TranscriptDTO(int id, int semester, String nameTranscript, String schoolYear) {
        this.id = id;
        this.semester = semester;
        this.nameTranscript = nameTranscript;
        this.schoolYear = schoolYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getNameTranscript() {
        return nameTranscript;
    }

    public void setNameTranscript(String nameTranscript) {
        this.nameTranscript = nameTranscript;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
