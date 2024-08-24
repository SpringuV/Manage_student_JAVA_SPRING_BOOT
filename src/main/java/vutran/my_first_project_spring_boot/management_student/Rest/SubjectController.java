package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-subject")
public class SubjectController {

    private SubjectService subjectService;
    private StudentService studentService;
    private SchoolService schoolService;
    private TeacherService teacherService;

    @Autowired
    public SubjectController(SubjectService subjectService, StudentService studentService,
            SchoolService schoolService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.schoolService = schoolService;
        this.teacherService = teacherService;
    }

    @GetMapping("/getSubjectBySchool/{schoolId}")
    @ResponseBody
    public List<Subject> returnListSubject(@PathVariable("schoolId") int schoolId){
        return  subjectService.getListSubjectOfSchoolId(schoolId);
    }

    @GetMapping("/showManageSubject")
    public String showManageSubject(Model model) {
        List<Subject> subjectList = subjectService.getAllSubject();
        // check List empty
        if (subjectList.isEmpty()) {
            model.addAttribute("Error", "Error, List Subject Empty !!!");
            model.addAttribute("subjectList", new ArrayList<>());
        }
        model.addAttribute("subjectList", subjectList);
        return "School/Subject/indexSubject";
    }

    @GetMapping("/showFormAddSubject")
    public String showAddForm(Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());
        model.addAttribute("subject", new Subject());
        return "School/Subject/addFormSubject";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute Subject subject, Model model) {
        // check subject exist
        Subject subjectExist = subjectService.getSubjectByName(subject.getNameSubject());
        if(subjectExist != null){
            model.addAttribute("Error", "Subject existed !!");
            model.addAttribute("subject", new Subject());
        } else {
            subjectService.addSubject(subject);
            model.addAttribute("success", "You created new Subject has Name: " + subject.getNameSubject());
            model.addAttribute("subject", subject);
        }
        return "School/Subject/addFormSubject";
    }

    @GetMapping("/showModifyFormSubject")
    public String showForm(@ModelAttribute Subject subject, Model model) {
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if (subjectExist == null) {
            model.addAttribute("Error", "Error, Not found Subject !!!");
            model.addAttribute("subject", new Subject());
            return "School/Subject/modifyFormSubject";
        } else {
            model.addAttribute("subject", subjectExist);
            return "School/Subject/modifyFormSubject";
        }
    }

    @PostMapping("/modify-process")
    public String processModify(@ModelAttribute Subject subject, Model model) {
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if (subjectExist == null) {
            model.addAttribute("Error", "Error, Not found Subject !!!");
            model.addAttribute("subject", new Subject());
            return "School/Subject/modifyFormSubject";
        } else {
            subjectExist.setNameSubject(subject.getNameSubject());
            subjectService.updateSubject(subjectExist);
            model.addAttribute("success", "You modified success subject has name: " + subjectExist.getNameSubject());
            model.addAttribute("subject", subjectExist);
            return "School/Subject/modifyFormSubject";
        }
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        // check exist
        Subject subjectExist = subjectService.getSubjectById(subject.getId());
        if (subjectExist == null) {
            redirectAttributes.addFlashAttribute("Error", "Error, Not found Subject !!!");
            redirectAttributes.addFlashAttribute("subject", new Subject());
            return "redirect:/m-subject/showManageSubject";
        } else {
            subjectExist.getSchoolList().clear();
            subjectExist.getStudentList().clear();
            subjectExist.getScoreCard().clear();
            teacherService.updateTeachersBySubjectId(subjectExist.getId());
            subjectExist.getTranscriptList().clear();
            subjectExist.getNoteBookDetailList().clear();
            subjectService.updateSubject(subjectExist);
            subjectService.deleteSubjectById(subjectExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted success subject have id: " + subject.getId());
            return "redirect:/m-subject/showManageSubject";
        }
    }
}
