package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-class")
public class ClassController {
    private ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/showManageClass")
    public String showClass(Model model){
        List<Classes> classesList = classService.getAllClasses();
        // check the class empty
        if(classesList.isEmpty()){
            model.addAttribute("classList", new ArrayList<>());
            model.addAttribute("Error", "Error, The List Empty!!");
            return "School/Classes/indexClass";
        } else {
            model.addAttribute("classList", classesList);
            return "School/Classes/indexClass";
        }
    }

    @GetMapping("/showFormAddClass")
    public String addClass(Model model){
        model.addAttribute("classes", new Classes());
        return "School/Classes/addFormClass";
    }

    @PostMapping("/add-process")
    public String addClass(@ModelAttribute Classes classes, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("classes", new Classes());
            model.addAttribute("Error", "Error, bindingResult");
            return "School/Classes/addFormClass";
        }
        // check class
        Classes classesExist = classService.findClassByNameAndSchoolId(classes.getName(), classes.getSchool().getId());
        if(classesExist != null){ // if exist will notify error
            model.addAttribute("Error", "Class existed");
            model.addAttribute("classes", new Classes());
            return "School/Classes/addFormClass";
        }
        classesExist = new Classes();
        classesExist.setName(classes.getName());
        classesExist.setSchool(classes.getSchool());
        classesExist.setGrade(classes.getGrade());
        classService.addClass(classesExist);
        model.addAttribute("success", "You created new Class");
        model.addAttribute("classes", classesExist);
        return "School/Classes/addFormClass";
    }

    @GetMapping("/showModifyFormClass")
    public String modifyForm(@ModelAttribute Classes classes,Model model){
        Classes classExist = classService.getClassById(classes.getId());

        // check student exist
        if(classExist != null){
            model.addAttribute("classes", classExist);
            return "School/Classes/formModifyClass";
        } else{
            model.addAttribute("classes", new Classes());
            model.addAttribute("Error", "Not found class");
            return "School/Classes/formModifyClass";
        }
    }

    @PostMapping("/modify-process")
    public String modifyClass(@ModelAttribute Classes classes, Model model){
        Classes classesExist = classService.getClassById(classes.getId());
        if(classesExist == null){
            model.addAttribute("Error", "Error, Not found class have id: "+classes.getId());
            model.addAttribute("classes", new Classes());
            return "School/Classes/formModifyClass";
        }
        classesExist.setSchool(classes.getSchool());
        classesExist.setName(classes.getName());
        classesExist.setGrade(classes.getGrade());
        classService.updateClass(classesExist);
        model.addAttribute("classes", classesExist);
        model.addAttribute("success", "You modified class id: "+ classesExist.getId());
        return "School/Classes/formModifyClass";
    }

}
