package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/m-teacher")
public class TeacherController {

    private TeacherService teacherService;
    private SchoolService schoolService;
    private AuthorityRepository authorityRepository;

    @Autowired
    public TeacherController(TeacherService teacherService, SchoolService schoolService, AuthorityRepository authorityRepository) {
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.authorityRepository = authorityRepository;
    }


    @GetMapping("/showManageTeacher")
    public String showTeacherList(Model model){
        // get list teacher from database
        List<Teacher> teacherList = teacherService.getListTeacherByPosition();

        if(teacherList.isEmpty()){
            model.addAttribute("errorTeacherList", "Not found teacher !!");
            model.addAttribute("teacherList", new ArrayList<>());
            return "Teacher/indexTeacher";
        }

        // forward to teacher form
        model.addAttribute("teacherList", teacherList);
        return "Teacher/indexTeacher";
    }

    @GetMapping("/showModifyFormTeacher")
    public String showForm(@Valid @ModelAttribute Teacher teacher, Model model){
        Teacher teacherExist = teacherService.getTeacherById(teacher.getId());

        if(teacherExist == null){
            model.addAttribute("teacher", new Teacher());
            model.addAttribute("Error", "Not found Teacher have id: "+teacher.getId());
            return "Teacher/modifyFormTeacher";
        }

        model.addAttribute("teacher", teacherExist);
        return "Teacher/modifyFormTeacher";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/modify-process")
    public String modifyProcess(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Errors in bindingResult");
            model.addAttribute("teacher", new Teacher());
            return "Teacher/modifyFormTeacher";
        }

        Teacher existTeacher = teacherService.getTeacherById(teacher.getId());
        if(existTeacher != null){
            existTeacher.setEmail(teacher.getEmail());
            existTeacher.setSchool(teacher.getSchool());
            existTeacher.setAddress(teacher.getAddress());
            existTeacher.setLastName(teacher.getLastName());
            existTeacher.setFirstName(teacher.getFirstName());
            existTeacher.setPhoneNumber(teacher.getPhoneNumber());
            teacherService.updateTeacher(existTeacher);
            model.addAttribute("teacher", teacher);
            model.addAttribute("success", "You updated teacher with id and name: "+ existTeacher.getId()+ " "+ existTeacher.getFirstName());
            return "Teacher/modifyFormTeacher";
        } else {
            model.addAttribute("Error", "Errors: Teacher null !!");
            model.addAttribute("teacher", new Teacher());
            return "Teacher/modifyFormTeacher";
        }
    }

    // New method to get school name by id
    @GetMapping("/getSchoolName/{id}")
    @ResponseBody
    public School getSchoolName(@PathVariable("id") int id) {
        return schoolService.getSchoolById(id);
    }

    // show form add teacher
    @GetMapping("showFormAddTeacher")
    public  String showForm(Model model){
        model.addAttribute("teacher", new Teacher());
        // show list school
        List<School> schoolList = schoolService.getAllSchools();
        if(schoolList.isEmpty()){
            model.addAttribute("Error", "List School is Empty");
            model.addAttribute("schoolList", new ArrayList<>());
        } else {
            model.addAttribute("schoolList", schoolList);
        }
        return "Teacher/addTeacher";
    }

    @PostMapping("/add-process")
    public String addTeacher(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("teacher", new Teacher());
            model.addAttribute("Error", "Error in BindingResult");
            return "Teacher/addTeacher";
        }
        // check teacher existed
        Teacher teacherExist = teacherService.fineTeacherByUserName(teacher.getUsername());

        // if teacher existed
        if(teacherExist != null){
            model.addAttribute("teacher", new Teacher());
            model.addAttribute("Error", "Teacher existed");
            return "Teacher/addTeacher";
        }
        // else create teacher
        Teacher newTeacher = new Teacher();
        newTeacher.setUsername(teacher.getUsername());
        // encode password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newTeacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        newTeacher.setEnabled(true);
        newTeacher.setFirstName(teacher.getFirstName());
        newTeacher.setLastName(teacher.getLastName());
        newTeacher.setIdentity(teacher.getIdentity());
        newTeacher.setEmail(teacher.getEmail());
        newTeacher.setPosition("Teacher");
        newTeacher.setSchool(teacher.getSchool());
        newTeacher.setPhoneNumber(teacher.getPhoneNumber());
        newTeacher.setAddress(teacher.getAddress());

        // set role teacher
        Authority authority = authorityRepository.findByName("ROLE_TEACHER");
        Collection<Authority> role = new ArrayList<>();
        role.add(authority);
        newTeacher.setCollectionAuthority(role);

        // save teacher
        teacherService.addTeacher(newTeacher);
        model.addAttribute("success", "You created new Teacher have id: " + newTeacher.getId()+" and username: "+ newTeacher.getUsername());
        model.addAttribute("teacher", newTeacher);
        return "Teacher/addTeacher";
    }

    @GetMapping("/modify-delete")
    public String deleteTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes){
        Teacher teacherExist = teacherService.getTeacherById(teacher.getId());
        if(teacherExist != null){
            teacherService.deleteTeacherById(teacherExist.getId());
            redirectAttributes.addFlashAttribute("success", "You have deleted teacher have id: "+ teacherExist.getId());
            List<Teacher> teacherList = teacherService.getListTeacherByPosition();
            redirectAttributes.addFlashAttribute("teacherList", teacherList);
            return "redirect:/m-teacher/showManageTeacher";
        } else {
            redirectAttributes.addFlashAttribute("Error", "Error process delete teacher, null !!");
            List<Teacher> teacherList = teacherService.getListTeacherByPosition();
            redirectAttributes.addFlashAttribute("teacherList", teacherList);
            return "redirect:/m-teacher/showManageTeacher";
        }
    }
}
