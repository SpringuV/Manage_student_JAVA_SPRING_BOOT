package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;
import vutran.my_first_project_spring_boot.management_student.Util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/m-student")
public class StudentController {

    private StudentService studentService;
    private AuthorityRepository authorityRepository;
    private SchoolService schoolService;
    private TeacherService teacherService;
    private ClassService classService;
    private StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService, AuthorityRepository authorityRepository, SchoolService schoolService, TeacherService teacherService, ClassService classService, StudentRepository studentRepository) {
        this.authorityRepository = authorityRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.classService = classService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/search-name")
    public String processSearch(@RequestParam("searchName") String searchName, Model model,@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size){
        Page<Student> studentPage = studentService.findStudentsByFirstName(searchName, PageRequest.of(page, size, Sort.by("firstName").ascending()));
        // check list
        if(studentPage.isEmpty()){
            model.addAttribute("Error", "Error, Search not found Student!!!");
            model.addAttribute("studentList", new ArrayList<>());
        } else {
            model.addAttribute("studentList", studentPage);
        }
        model.addAttribute("searchName", searchName);
        return "Student/indexStudent";
    }

    @GetMapping("/showManageStudent")
    public String showListStudent(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "15") int size){
        Page<Student> studentList = studentRepository.findAll(PageRequest.of(page, size, Sort.by("firstName").ascending()));
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
        model.addAttribute("schoolList", schoolService.getAllSchools());
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
        Student studentExistUserName = studentService.getStudentByUserName(student.getUsername());
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
            newStudent.setLastName(StringUtils.formatString(student.getLastName()));
            newStudent.setFirstName(StringUtils.formatString(student.getFirstName()));
            if(student.getTeacher().getId() != 0){
                newStudent.setTeacher(student.getTeacher());
            }
            newStudent.setAvatar(student.getAvatar());
            newStudent.setIdentity(student.getIdentity());
            if(student.getSchool().getId() != 0){
                newStudent.setSchool(student.getSchool());
            }
            newStudent.setAddress(StringUtils.formatString(student.getAddress()));
            newStudent.setEmail(student.getEmail());
            newStudent.setEnabled(true);
            newStudent.setParent(student.getParent());
            if(student.getClasses().getId() != 0){
                newStudent.setClasses(student.getClasses());
            }
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

    @PostMapping("/showModifyFormStudent")
    public String formModifyStudent(@RequestParam("idStudent") int idStudent,Model model){
        Student studentExist = studentService.getStudentById(idStudent);

        // check student exist
        if(studentExist != null){
            model.addAttribute("student", studentExist);
            model.addAttribute("schoolList", schoolService.getAllSchools());
            return "Student/modifyFormStudent";
        } else{
            model.addAttribute("student", new Student());
            model.addAttribute("Error", "Not found student has id: "+idStudent);
            model.addAttribute("schoolList", schoolService.getAllSchools());
            return "Student/modifyFormStudent";
        }
    }

    @PostMapping("/modify-process")
    public String modifyStudent(@ModelAttribute Student student ,Model model){
        // check student exist
        Student studentExist = studentService.getStudentById(student.getId());
        // exits, modify
        if(studentExist != null){
            studentExist.setFirstName(StringUtils.formatString(student.getFirstName()));
            studentExist.setLastName(StringUtils.formatString(student.getLastName()));
            studentExist.setAddress(StringUtils.formatString(student.getAddress()));
            if(student.getSchool().getId() != 0 && student.getSchool() != null){
                studentExist.setSchool(student.getSchool());
            }
            studentExist.setIdentity(student.getIdentity());
            studentExist.setEmail(student.getEmail());
            studentExist.setPhoneNumber(student.getPhoneNumber());
            if(student.getTeacher() != null && student.getTeacher().getId() != 0){
                studentExist.setTeacher(student.getTeacher());
            }
            if(student.getClasses().getId() != 0 && student.getClasses() != null){
                studentExist.setClasses(student.getClasses());
            }
            studentService.updateStudent(studentExist);
            model.addAttribute("student", student);
            model.addAttribute("success", "You modified student have id: " + studentExist.getId());
            return "Student/modifyFormStudent";
        }
        // else: notify error
        else{
            model.addAttribute("student", new Student());
            model.addAttribute("Error", "Error in process modify student");
            return "Student/modifyFormStudent";
        }
    }

    @PostMapping("modify-delete")
    public String processDelete(@RequestParam("idStudent") int idStudent, RedirectAttributes redirectAttributes){
        // check student exist
        Student studentExist = studentService.getStudentById(idStudent);
        if(studentExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, Student doesn't exist!!");
            return "redirect:/m-student/showManageStudent";
        } else {
            // delete
            studentService.deleteStudentById(studentExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted student have id: "+studentExist.getId()+" Name: "+studentExist.getFirstName());
            return "redirect:/m-student/showManageStudent";
        }
    }

}
