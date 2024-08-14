package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-school")
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
            model.addAttribute("Error", "List School empty");
            model.addAttribute("schoolList", new ArrayList<>());
            return "School/indexSchool";
        }

        // forward to Form
        model.addAttribute("schoolList", schoolList);
        return "School/indexSchool";
    }

    @GetMapping("/showFormAddSchool")
    public String showFormAddSchool(Model model){
        School addSchool = new School();
        model.addAttribute("school", addSchool);
        return "School/addForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // add new school
    @PostMapping("/add-process")
    public String processAddSchool(@Valid @ModelAttribute School addSchool, BindingResult bindingResult, Model model, HttpSession httpSession){
        String schoolName = addSchool.getName();

        // form validation
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Error");
            return "School/addForm";
        }

        // check schoolExisted
        School schoolExisted = schoolService.findBySchoolName(schoolName);
        if(schoolExisted != null){
            model.addAttribute("school", new School());
            model.addAttribute("Error", "School Existed");
            return "School/addForm";
        }

        // school doesn't exist
        School school = new School();
        school.setName(addSchool.getName());
        school.setAddress(addSchool.getAddress());
        school.setPhone(addSchool.getPhone());
        school.setLevel(addSchool.getLevel());
        schoolService.addSchool(school);

        // notify success
        model.addAttribute("success","You created new School has School Name: "+ school.getName());
        model.addAttribute("school", school);
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
    public String processDelete(@Valid @ModelAttribute School school , RedirectAttributes redirectAttributes){
        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool != null){
            schoolService.deleteSchoolById(existSchool.getId());
            List<School> schoolList = schoolService.getAllSchools();
            redirectAttributes.addAttribute("schoolList", schoolList);
            redirectAttributes.addAttribute("success","School deleted successfully school with id " + existSchool.getId());
//        return "redirect:/api-school/showManageSchool";
            return "redirect:/api-school/showManageSchool";
        } else{
            List<School> schoolList = schoolService.getAllSchools();
            redirectAttributes.addAttribute("schoolList", schoolList);
            redirectAttributes.addAttribute("Error","Error process delete School, null !!");
            return "redirect:/api-school/showManageSchool";
        }
    }
}
