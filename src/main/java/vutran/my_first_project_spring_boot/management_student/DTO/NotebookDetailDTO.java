package vutran.my_first_project_spring_boot.management_student.DTO;

import java.sql.Date;

public class NotebookDetailDTO {
    private int id;
    private String contentLecture, time, teacherComment;
    private Date teachingDay;

    public NotebookDetailDTO() {
    }

    public NotebookDetailDTO(int id, String contentLecture, String time, String teacherComment, Date teachingDay) {
        this.id = id;
        this.contentLecture = contentLecture;
        this.time = time;
        this.teacherComment = teacherComment;
        this.teachingDay = teachingDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentLecture() {
        return contentLecture;
    }

    public void setContentLecture(String contentLecture) {
        this.contentLecture = contentLecture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public Date getTeachingDay() {
        return teachingDay;
    }

    public void setTeachingDay(Date teachingDay) {
        this.teachingDay = teachingDay;
    }
}
