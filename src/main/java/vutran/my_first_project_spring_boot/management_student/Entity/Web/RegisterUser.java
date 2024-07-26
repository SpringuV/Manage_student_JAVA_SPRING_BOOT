package vutran.my_first_project_spring_boot.management_student.Entity.Web;

import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterUser {
    @NotBlank(message = "Required Information")
    @Size(min = 1, message = "Min length is 1")
    private String username;

    @NotBlank(message = "Required Information")
    @Size(min = 4, message = "Min length is 4")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).*$", message = "Password length at least 4 characters")
    private String password;
    @NotBlank(message = "Required Information")
    private String lastName;
    @NotBlank(message = "Required Information")
    private String firstName;
    @NotBlank(message = "Required Information")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Required Information")
    private String position;
    @NotBlank(message = "Required Information")
    private String identity;


    public RegisterUser() {
    }

    @Autowired
    public RegisterUser(String username, String password, String lastName, String firstName, String email, String position, String identity) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.position = position;
        this.identity = identity;
    }

    public @NotBlank(message = "Required Information") String getIdentity() {
        return identity;
    }

    public void setIdentity(@NotBlank(message = "Required Information") String identity) {
        this.identity = identity;
    }

    public @NotBlank(message = "Required Information") String getPosition() {
        return position;
    }

    public void setPosition(@NotBlank(message = "Required Information") String position) {
        this.position = position;
    }

    public @NotBlank(message = "Required Information") @Size(min = 1, message = "Min length is 1") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Required Information") @Size(min = 1, message = "Min length is 1") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Required Information") @Size(min = 4, message = "Min length is 4") @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).*$", message = "Password length at least 4 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Required Information") @Size(min = 4, message = "Min length is 4") @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).*$", message = "Password length at least 4 characters") String password) {
        this.password = password;
    }
    public @NotBlank(message = "Required Information") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Required Information") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Required Information") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Required Information") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Required Information") @Email(message = "Invalid Email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Required Information") @Email(message = "Invalid Email") String email) {
        this.email = email;
    }
}
