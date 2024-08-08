package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-subject")
public class SubjectController {

    private SubjectService subjectService;
    private StudentService studentService;
    private SchoolService schoolService;

    @Autowired
    public SubjectController(SubjectService subjectService, StudentService studentService, SchoolService schoolService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.schoolService = schoolService;
    }

    @GetMapping("/showManageSubject")
    public String showManageSubject(Model model){
        List<Subject> subjectList = subjectService.getAllSubject();
        // check List empty
        if (subjectList.isEmpty()){
            model.addAttribute("Error", "Error, List Subject Empty !!!");
            model.addAttribute("subjectList", new ArrayList<>());
        }
        model.addAttribute("subjectList", subjectList);
        return "School/Subject/indexSubject";
    }

    @GetMapping("/showFormAddSubject")
    public String showAddForm(Model model){
        model.addAttribute("schools", schoolService.getAllSchools());
        model.addAttribute("subject", new Subject());
        return "School/Subject/addFormSubject";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute Subject subject ,Model model){
        // check subject exist
        boolean checkExist = false;
        for(School school : subject.getSchoolList()){
            Subject subjectExist = subjectService.getSubjectBySchoolIdAndName(school.getId(), subject.getNameSubject());
            if(subjectExist != null){
                checkExist = true; // subject exited
                // notify error
                model.addAttribute("Error", "Error, Subject existed with school Id: " + school.getId()+"("+school.getName()+") and Name subject: "+ subject.getNameSubject());
                break;
            }
        }
        // true
        if(checkExist){
            model.addAttribute("students", studentService.findALlStudentByPosition());
            model.addAttribute("schools", schoolService.getAllSchools());
            model.addAttribute("subject", new Subject());
            return "School/Subject/addFormSubject";
        } else {
            // false
            // add subject
            subjectService.addSubject(subject);
            model.addAttribute("success","You created new Subject");
            model.addAttribute("subject", subject);
            return "School/Subject/addFormSubject";
        }
    }

    @GetMapping("/showModifyFormSubject")
    public String showForm(@ModelAttribute Subject subject, Model model){
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if(subjectExist == null){
            model.addAttribute("Error", "Error, Not found Subject !!!");
            model.addAttribute("subject", new Subject());
            return "School/Subject/modifyFormSubject";
        } else {
            model.addAttribute("subject", subjectExist);
            model.addAttribute("schools", schoolService.getAllSchools());
            return "School/Subject/modifyFormSubject";
        }
    }

    @PostMapping("/modify-process")
    public String processModify(@ModelAttribute Subject subject, Model model){
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if(subjectExist == null){
            model.addAttribute("Error", "Error, Not found Subject !!!");
            model.addAttribute("subject", new Subject());
            return "School/Subject/modifyFormSubject";
        } else {
            subjectExist.setNameSubject(subject.getNameSubject());
            subjectExist.setSchoolList(subject.getSchoolList());
            subjectService.updateSubject(subjectExist);
            model.addAttribute("subject", subjectExist);
            return "School/Subject/modifyFormSubject";
        }
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes){
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if(subjectExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, Not found Subject !!!");
            redirectAttributes.addFlashAttribute("subject", new Subject());
            return "redirect:/m-subject/showManageSubject";
        } else {
            subjectService.deleteSubjectById(subject.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted success subject have id: " + subject.getId());
            return "redirect:/m-subject/showManageSubject";
        }
    }
}
