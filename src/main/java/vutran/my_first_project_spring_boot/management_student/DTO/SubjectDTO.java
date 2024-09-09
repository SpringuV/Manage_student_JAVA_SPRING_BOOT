package vutran.my_first_project_spring_boot.management_student.DTO;

public class SubjectDTO {
    private int id;
    private String nameSubject, sub_level;

    public SubjectDTO() {
    }

    public SubjectDTO(int id, String nameSubject, String sub_level) {
        this.id = id;
        this.nameSubject = nameSubject;
        this.sub_level = sub_level;
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

    public String getSub_level() {
        return sub_level;
    }

    public void setSub_level(String sub_level) {
        this.sub_level = sub_level;
    }
}
