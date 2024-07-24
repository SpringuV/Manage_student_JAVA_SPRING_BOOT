package vutran.my_first_project_spring_boot.management_student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class ManageStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageStudentApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(UserService userService, AuthorityRepository authorityRepository){
//		return runner-> {
//			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//			User userNew = new User();
//			userNew.setUsername("vutran");
//			userNew.setPassword(bCryptPasswordEncoder.encode("1234a@"));
//			userNew.setEnabled(true);
//
//			// create authority
//			Authority defaultRole = authorityRepository.findByName("ROLE_ADMIN");
//			Collection<Authority> roles = new ArrayList<>();
//			roles.add(defaultRole);
//			userNew.setCollectionAuthority(roles);
//
//			User userNew2 = new User();
//			userNew2.setUsername("teach1");
//			userNew2.setPassword(bCryptPasswordEncoder.encode("1234a@"));
//			userNew2.setEnabled(true);
//
//			// create authority
//			Authority defaultRole2 = authorityRepository.findByName("ROLE_TEACHER");
//			Collection<Authority> roles2 = new ArrayList<>();
//			roles2.add(defaultRole2);
//			userNew2.setCollectionAuthority(roles2);
//
//			userService.addUser(userNew);
//			userService.addUser(userNew2);
//
//			User userNew3 = new User();
//			userNew3.setEnabled(true);
//			userNew3.setUsername("manager1");
//			userNew3.setPassword(bCryptPasswordEncoder.encode("1234a@"));
//
//			// create authority
//			Authority defaultRole3 = authorityRepository.findByName("ROLE_MANAGER");
//			Collection<Authority> roles3 = new ArrayList<>();
//			roles3.add(defaultRole3);
//			userNew3.setCollectionAuthority(roles3);
//
//			userService.addUser(userNew3);
//		};
//	}
}
