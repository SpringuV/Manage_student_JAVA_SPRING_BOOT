package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Web.AddSchool;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api-school")
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/showManageSchool")
    public String showSchool(Model model){
        // get school from database
        List<School> schoolList = schoolService.getAllSchools();
        // check size
        if (schoolList.isEmpty()){
            model.addAttribute("errorSchoolList", "List School empty");
            model.addAttribute("schoolList", new ArrayList<>());
            return "School/indexSchool";
        }

        // forward to Form
        model.addAttribute("schoolList", schoolList);
        return "School/indexSchool";
    }

    @GetMapping("/showFormAddSchool")
    public String showFormAddSchool(Model model){
        AddSchool addSchool = new AddSchool();
        model.addAttribute("addSchool", addSchool);
        return "School/addForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // add new school
    @PostMapping("/add-process")
    public String processAddSchool(@Valid @ModelAttribute AddSchool addSchool, BindingResult bindingResult, Model model, HttpSession httpSession){
        String schoolName = addSchool.getSchoolName();

        // form validation
        if(bindingResult.hasErrors()){
            model.addAttribute("myError", "Error");
            return "School/addForm";
        }

        // check schoolExisted
        School schoolExisted = schoolService.findBySchoolName(schoolName);
        if(schoolExisted != null){
            model.addAttribute("addSchool", new AddSchool());
            model.addAttribute("myError", "School Existed");
            return "School/addForm";
        }

        // school doesn't exist
        School school = new School();
        school.setName(addSchool.getSchoolName());
        school.setAddress(addSchool.getSchoolAddress());
        school.setPhone(addSchool.getSchoolPhone());
        school.setLevel(addSchool.getSchoolLevel());
        schoolService.addSchool(school);

        // notify success
        model.addAttribute("mySchool", school);
        return "School/addForm";
    }

    // modify school
    @GetMapping("/showModifyForm")
    public String showModifyForm(@Valid @ModelAttribute School school, Model model){

        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool == null){
            model.addAttribute("school", new School());
            model.addAttribute("Error", "Not found school with id: "+ school.getId());
            return "School/indexSchool";
        }

        model.addAttribute("school", existSchool);
        return "School/modifyForm";
    }

    @PostMapping("/modify-process")
    public String processModify(@Valid @ModelAttribute School school, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Errors");
            model.addAttribute("school", new School());
            return "School/modifyForm";
        }

        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool != null){
            existSchool.setName(school.getName());
            existSchool.setAddress(school.getAddress());
            existSchool.setPhone(school.getPhone());
            existSchool.setLevel(school.getLevel());
            schoolService.updateSchool(existSchool);
        }
        model.addAttribute("success", "School updated successfully school with id " + existSchool.getId());
        model.addAttribute("school", existSchool);
        return "School/modifyForm";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@Valid @ModelAttribute School school, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Errors");
            return "School/indexSchool";
        }
        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool != null){
            schoolService.deleteSchoolById(existSchool.getId());
        }
        List<School> schoolList = schoolService.getAllSchools();
        model.addAttribute("schoolList", schoolList);
        model.addAttribute("success","School deleted successfully school with id " + existSchool.getId());
//        return "redirect:/api-school/showManageSchool";
        return "School/indexSchool";
    }
}
