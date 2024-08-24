package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.ScoreCardService;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;
import vutran.my_first_project_spring_boot.management_student.Service.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-score-card")
public class ScorecardController {
    private ScoreCardService scoreCardService;
    private SubjectService subjectService;
    private SchoolService schoolService;
    private StudentService studentService;

    @Autowired
    public ScorecardController(ScoreCardService scoreCardService, SubjectService subjectService, SchoolService schoolService, StudentService studentService) {
        this.scoreCardService = scoreCardService;
        this.subjectService = subjectService;
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping("/showManageScoreCard")
    public String showManage(Model model){
        List<ScoreCard> scoreCardList = scoreCardService.getAllScoreCard();
        // check list empty
        if(scoreCardList.isEmpty()){
            model.addAttribute("Error", "Error, List ScoreCard Empty !!!");
            model.addAttribute("scoreCardList", new ArrayList<>());
        } else {
            model.addAttribute("scoreCardList", scoreCardList);
        }
         return "School/Subject/ScoreCard/indexScoreCard";
    }


    @GetMapping("/showFormAddScoreCard")
    public String showAdd(@ModelAttribute ScoreCard scoreCard, Model model){
        model.addAttribute("scoreCard", scoreCard);
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "School/Subject/ScoreCard/addFormScoreCard";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute ScoreCard scoreCard, Model model, RedirectAttributes redirectAttributes){
        // check score card Exist
        School school = schoolService.getSchoolById(scoreCard.getSchool().getId());
        // if school not exist
        if(school == null){
            model.addAttribute("Error", "Not found school !!!");
            return "School/Subject/ScoreCard/addFormScoreCard";
        }
        ScoreCard scoreCardExist = scoreCardService.getScoreCardBySchoolYear_NameExam_Student_Subject(scoreCard.getSchoolYear(), scoreCard.getNameExam(), scoreCard.getStudent().getId(), scoreCard.getSubject().getId());
        if(scoreCardExist != null){
            // if exist
            redirectAttributes.addFlashAttribute("Error", "Error, Score Card Existed !!!");
            return "redirect:/m-score-card/showFormAddScoreCard";
        } else {
            // doesn't exist, add score card
            ScoreCard newScoreCard = new ScoreCard();
            newScoreCard.setDayExam(scoreCard.getDayExam());
            newScoreCard.setStudent(scoreCard.getStudent());
            newScoreCard.setNameExam(scoreCard.getNameExam());
            newScoreCard.setScore(scoreCard.getScore());
            newScoreCard.setSchoolYear(scoreCard.getSchoolYear());
            newScoreCard.setSubject(scoreCard.getSubject());
            newScoreCard.setSchool(scoreCard.getSchool());
            scoreCardService.addScoreCard(newScoreCard);
            model.addAttribute("success", "You created new Score Card has Student id: "+ newScoreCard.getStudent().getId());
            return "School/Subject/ScoreCard/notify";
        }
    }

    @GetMapping("/showModifyFormScoreCard")
    public String showFormModify(@ModelAttribute ScoreCard scoreCard, Model model, RedirectAttributes redirectAttributes){
        // check scoreCard exist
        ScoreCard scoreCardExist = scoreCardService.getScoreCardById(scoreCard.getId());

        if(scoreCardExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, ScoreCard Not Found !!!");
            return "redirect:/m-score-card/showManageScoreCard";
        } else {
            model.addAttribute("scoreCard", scoreCardExist);
            return "School/Subject/ScoreCard/modifyFormScoreCard";
        }
    }

    @PostMapping("/modify-process")
    public String modifyProcess(@ModelAttribute ScoreCard scoreCard, Model model, RedirectAttributes redirectAttributes){
        // check scoreCard exist
        ScoreCard scoreCardExist = scoreCardService.getScoreCardById(scoreCard.getId());
        if(scoreCardExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, ScoreCard Not Found !!!");
            return "redirect:/m-score-card/showManageScoreCard";
        } else {
            scoreCardExist.setNameExam(scoreCard.getNameExam());
            scoreCardExist.setScore(scoreCard.getScore());
            scoreCardExist.setSchoolYear(scoreCard.getSchoolYear());
            scoreCardExist.setSubject(scoreCard.getSubject());
            scoreCardExist.setDayExam(scoreCard.getDayExam());
            scoreCardService.updateScoreCard(scoreCardExist);
            model.addAttribute("success", "You modified score card has id: "+scoreCardExist.getId());
            model.addAttribute("scoreCard", scoreCardExist);
            return "School/Subject/ScoreCard/modifyFormScoreCard";
        }
    }

    @GetMapping("/modify-delete")
    public String modifyDelete(@ModelAttribute ScoreCard scoreCard, RedirectAttributes redirectAttributes){
        // check scoreCard exist
        ScoreCard scoreCardExist = scoreCardService.getScoreCardById(scoreCard.getId());
        if(scoreCardExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, ScoreCard Not Found !!!");
            return "redirect:/m-score-card/showManageScoreCard";
        } else {
            scoreCardService.deleteScoreCardById(scoreCardExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted score card has id: "+scoreCard.getId());
            return "redirect:/m-score-card/showManageScoreCard";
        }
    }
}
