package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-teacher")
public class TeacherController {

    private TeacherService teacherService;
    private SchoolService schoolService;

    @Autowired
    public TeacherController(TeacherService teacherService, SchoolService schoolService) {
        this.teacherService = teacherService;
        this.schoolService = schoolService;
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
}
