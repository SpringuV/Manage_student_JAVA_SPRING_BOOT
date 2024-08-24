package vutran.my_first_project_spring_boot.management_student.Entity;

import java.util.Date;

public class ErrorResponse {
    private String message;
    private Date timeStamp;
    private int status;

    public ErrorResponse(int status, String message ) {
        this.message = message;
        this.timeStamp = new Date();
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
