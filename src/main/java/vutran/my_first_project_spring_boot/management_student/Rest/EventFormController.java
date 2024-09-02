package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class EventFormController {
    private UserService userService;
    private AuthorityRepository authorityRepository;
    private SchoolService schoolService;
    private ClassService classService;
    private SubjectService subjectService;
    private StudentService studentService;
    private TeacherService teacherService;

    @Autowired
    public EventFormController(UserService userService, TeacherService teacherService, StudentService studentService, AuthorityRepository authorityRepository,SubjectService subjectService, SchoolService schoolService, ClassService classService){
        this.userService = userService;
        this.subjectService = subjectService;
        this.authorityRepository = authorityRepository;
        this.schoolService = schoolService;
        this.classService = classService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/event/getTeacherBySchoolAndClass/{schoolId}/{classId}")
    @ResponseBody
    public List<Teacher> returnListTeacher(@PathVariable int schoolId, @PathVariable int classId){
        return teacherService.getListTeacherBySchoolIdAndClassID(schoolId, classId);
    }

    @GetMapping("/event/getStudentByClassAndSchool/{schoolId}/{classId}")
    @ResponseBody
    public List<Student> getListStudent(@PathVariable("schoolId") int school_id, @PathVariable("classId") int class_id){
        return  studentService.getListStudentByClassAndSchool(class_id, school_id);
    }


    @GetMapping("/event/getSubjectBySchool/{schoolId}")
    @ResponseBody
    public List<Subject> returnListSubject(@PathVariable int schoolId){
        return subjectService.getListSubjectOfSchoolId(schoolId);
    }

    @GetMapping("/event/getSchoolById/{schoolId}")
    @ResponseBody
    public School returnSchool(@PathVariable("schoolId") int id_school){
        return  schoolService.getSchoolById(id_school);
    }

    @GetMapping("/event/getClassBySchoolId/{schoolId}")
    @ResponseBody
    public List<Classes> returnListClass(@PathVariable("schoolId") int schoolId){
        return classService.getListClassByIdSchool(schoolId);
    }

    // after login success return to home
    @GetMapping()
    public String showHomePage(Model model){
        return "home";
    }

    //homepage
    @GetMapping("/backHomepage")
    public String returnHomePage(){
        return "home";
    }

    // login
    @GetMapping("/showLoginPage")
    public String showLoginPage(@RequestParam(value = "expired", required = false) String expired, Model model){
        // Có thể kiểm tra xem tham số expired có tồn tại hay không
        if (expired != null) {
            // Xử lý logic nếu cần khi tham số expired tồn tại
            model.addAttribute("error", "Your session has expired. Please log in again.");
        }
        return "login";
    }

    //403
    @GetMapping("/showPage403")
    public String showPage403(){
        return "Error/403";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
