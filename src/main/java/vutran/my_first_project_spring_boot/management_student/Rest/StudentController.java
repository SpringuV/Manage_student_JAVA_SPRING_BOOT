package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/m-student")
public class StudentController {

    private StudentService studentService;
    private AuthorityRepository authorityRepository;

    @Autowired
    public StudentController(StudentService studentService, AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        this.studentService = studentService;
    }

    @GetMapping("/showManageStudent")
    public String showListStudent(Model model){
        List<Student> studentList = studentService.findALlStudentByPosition();
        //check List
        if(studentList.isEmpty()){
            model.addAttribute("studentList", new ArrayList<>());
            model.addAttribute("Error", "Error, List Student is empty!!");
            return "Student/indexStudent";
        }
        // else
        model.addAttribute("studentList", studentList);
        return "Student/indexStudent";
    }

    @GetMapping("/showFormAddStudent")
    public String showFormAddStudent(Model model){
        model.addAttribute("student", new Student());
        return "Student/addStudentForm";
    }

    @PostMapping("/add-process")
    public String addStudentProcess(@ModelAttribute Student student, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Error in process Addstudent!!");
            model.addAttribute("student", new Student());
            return "Student/addStudentForm";
        }
        // check student exist
        Student studentExistIdentity = studentService.getStudentByIdentity(student.getIdentity());
        Student studentExistUserName = studentService.getStudentByUsername(student.getUsername());
        if(studentExistIdentity != null){
            model.addAttribute("Error", "Error, Student existed !!");
            model.addAttribute("student", new Student());
            return "Student/addStudentForm";
        } else if (studentExistUserName != null){
            model.addAttribute("Error", "Error, Student existed !!");
            model.addAttribute("student", new Student());
            return "Student/addStudentForm";
        } else {
            Student newStudent = new Student();
            newStudent.setPhoneNumber(student.getPhoneNumber());
            newStudent.setLastName(student.getLastName());
            newStudent.setFirstName(student.getFirstName());
            newStudent.setTeacher(student.getTeacher());
            newStudent.setSchool(student.getSchool());
            newStudent.setAddress(student.getAddress());
            newStudent.setEnabled(true);
            newStudent.setParent(student.getParent());
            newStudent.setClasses(student.getClasses());
            newStudent.setUsername(student.getUsername());
            // bcrypt
            newStudent.setPassword(new BCryptPasswordEncoder().encode(student.getPassword()));
            newStudent.setPosition("Student");
            // authority
            Authority authority = authorityRepository.findByName("ROLE_STUDENT");
            Collection<Authority> roles = new ArrayList<>();
            roles.add(authority);
            newStudent.setCollectionAuthority(roles);

            studentService.addStudent(newStudent);
            model.addAttribute("success", "You created account student with id: "+ newStudent.getId()+" and UserName: "+newStudent.getUsername());
            model.addAttribute("student", newStudent);
            return "Student/addStudentForm";
        }
    }

    @GetMapping("modify-delete")
    public String processDelete(@ModelAttribute Student student, RedirectAttributes redirectAttributes){
        // check student exist
        Student studentExist = studentService.getStudentById(student.getId());
        if(studentExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, Student doesn't exist!!");
            return "redirect:/m-student/showManageStudent";
        } else {
            studentService.deleteStudentById(studentExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted student have id: "+studentExist.getId()+" Name: "+studentExist.getFirstName()+" success");
            return "redirect:/m-student/showManageStudent";
        }
    }

}
