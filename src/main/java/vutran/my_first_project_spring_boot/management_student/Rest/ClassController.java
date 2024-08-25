package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vutran.my_first_project_spring_boot.management_student.Dao.ClassRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-class")
public class ClassController {
    private ClassService classService;
    private TeacherService teacherService;
    private SchoolService schoolService;
    private ClassRepository classRepository;

    @Autowired
    public ClassController(ClassRepository classRepository, ClassService classService, TeacherService teacherService, SchoolService schoolService) {
        this.classService = classService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.classRepository = classRepository;
    }

    @GetMapping("/showManageClass")
    public String showClass(Model model, @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size){
        Page<Classes> classesList = classRepository.findAll(PageRequest.of(page, size));
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

    @GetMapping("/getSchoolById/{schoolId}")
    @ResponseBody
    public School returnSchool(@PathVariable("schoolId") int id_school){
        return  schoolService.getSchoolById(id_school);
    }

    @GetMapping("/getClassBySchoolId/{schoolId}")
    @ResponseBody
    public List<Classes> returnListClass(@PathVariable("schoolId") int schoolId){
        return classService.getListClassByIdSchool(schoolId);
    }

    @GetMapping("/getClassByStudentAndSchool/{schoolId}/{studentId}")
    @ResponseBody
    public Classes returnClass(@PathVariable("schoolId") int school_id, @PathVariable("studentId") int student_id){
        return  classService.getClassByStudentAndSchool(school_id, student_id);
    }

    @GetMapping("/showFormAddClass")
    public String addClass(Model model){
        model.addAttribute("classes", new Classes());
        List<School> schoolList = schoolService.getAllSchools();
        model.addAttribute("schoolList", schoolList);
        return "School/Classes/addFormClass";
    }

    @PostMapping("/add-process")
    public String addClass(@ModelAttribute Classes classes, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("classes", new Classes());
            model.addAttribute("Error", "Error, bindingResult add process "+bindingResult.getFieldError().toString());
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
            model.addAttribute("schoolList", schoolService.getAllSchools());
            return "School/Classes/formModifyClass";
        } else{
            model.addAttribute("classes", new Classes());
            model.addAttribute("Error", "Not found class");
            model.addAttribute("schoolList", schoolService.getAllSchools());
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

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute Classes classes, RedirectAttributes redirectAttributes){
        Classes classExist = classService.getClassById(classes.getId());
        if (classExist == null) {
            redirectAttributes.addFlashAttribute("Error", "Error, Not found Class !!!");
        } else {
            classService.deleteClassById(classExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted success classes has id: "+classExist.getId() + "belong school "+ classExist.getSchool().getName());
        }
        return "redirect:/m-class/showManageClass";
    }
}
