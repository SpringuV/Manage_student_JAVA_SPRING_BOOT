package vutran.my_first_project_spring_boot.management_student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vutran.my_first_project_spring_boot.management_student.Service.*;

@SpringBootApplication
public class ManageStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageStudentApplication.class, args);
	}
}
