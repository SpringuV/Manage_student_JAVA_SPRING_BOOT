package vutran.my_first_project_spring_boot.management_student.Entity.Web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

public class AddSchool {
    @NotBlank(message = "Required Infomation")
    @Size(min = 10, message = "Min length is 10")
    private String schoolName;

    @NotBlank(message = "Required Infomation")
    @Size(min = 10, message = "Min length is 10")
    private String schoolAddress;

    @NotBlank(message = "Required Infomation")
    @Size(min = 10, message = "Min length is 10")
    private String schoolPhone;

    @NotBlank(message = "Required Infomation")
    @Size(min = 5, message = "Min length is 10")
    private String schoolLevel;

    public AddSchool() {
    }

    @Autowired
    public AddSchool(String schoolPhone, String schoolAddress, String schoolName, String schoolLevel) {
        this.schoolPhone = schoolPhone;
        this.schoolAddress = schoolAddress;
        this.schoolName = schoolName;
        this.schoolLevel = schoolLevel;
    }

    public @NotBlank(message = "Required Infomation") @Size(min = 5, message = "Min length is 10") String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(@NotBlank(message = "Required Infomation") @Size(min = 5, message = "Min length is 10") String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public @NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(@NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String schoolName) {
        this.schoolName = schoolName;
    }

    public @NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(@NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public @NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(@NotBlank(message = "Required Infomation") @Size(min = 10, message = "Min length is 10") String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }
}
