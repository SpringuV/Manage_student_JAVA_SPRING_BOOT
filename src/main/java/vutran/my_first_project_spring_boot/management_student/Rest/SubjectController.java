package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private TeacherService teacherService;
    private ScoreCardService scoreCardService;

    @Autowired
    public SubjectController(SubjectService subjectService, StudentService studentService, SchoolService schoolService, TeacherService teacherService, ScoreCardService scoreCardService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.schoolService = schoolService;
        this.teacherService = teacherService;
        this.scoreCardService = scoreCardService;
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
        model.addAttribute("students", studentService.findALlStudentByPosition());
        model.addAttribute("teachers", teacherService.getListTeacherByPosition());
        model.addAttribute("schools", schoolService.getAllSchools());
        model.addAttribute("scoreCards", scoreCardService.getAllScoreCard());
        model.addAttribute("subject", new Subject());
        return "School/Subject/addFormSubject";
    }


}
