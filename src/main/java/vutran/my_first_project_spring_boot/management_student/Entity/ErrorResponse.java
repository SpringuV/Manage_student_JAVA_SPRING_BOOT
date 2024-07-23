package vutran.my_first_project_spring_boot.management_student.Entity;

public class ErrorResponse {
    private String message;
    private long timeStamp;
    private int status;

    public ErrorResponse(int status, String message ) {
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
