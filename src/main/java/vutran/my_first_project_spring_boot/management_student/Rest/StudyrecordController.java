package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.StudyrecordRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.StudyrecordService;
import vutran.my_first_project_spring_boot.management_student.Service.TranscriptService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-study")
public class StudyrecordController {
    private StudyrecordService studyrecordService;
    private TranscriptService transcriptService;
    private SchoolService schoolService;
    private StudyrecordRepository studyrecordRepository;

    @Autowired
    public StudyrecordController(StudyrecordRepository studyrecordRepository, StudyrecordService studyrecordService, TranscriptService transcriptService, SchoolService schoolService) {
        this.studyrecordService = studyrecordService;
        this.transcriptService = transcriptService;
        this.schoolService = schoolService;
        this.studyrecordRepository = studyrecordRepository;
    }

    @GetMapping("/showManageStudyRecord")
    public String showManage(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size){
        Page<StudyRecord> studyRecordList = studyrecordRepository.findAll(PageRequest.of(page, size));
        // check list empty
        if(studyRecordList.isEmpty()){
            model.addAttribute("Error", "Error, The List Empty!!!");
            model.addAttribute("studyRecordList", new ArrayList<>());
        } else {
            model.addAttribute("studyRecordList", studyRecordList);
        }
         return "School/StudyRecord/indexStudyRecord";
    }

    @GetMapping("/showFormAddStudyRecord")
    public String showFormAdd(Model model){
        model.addAttribute("studyRecord", new StudyRecord());
        // get list transcript
        List<Transcript> transcriptList = transcriptService.getAllTranscripts();
        if(transcriptList.isEmpty()){
            model.addAttribute("transcripts", new ArrayList<>());
            model.addAttribute("schoolList", schoolService.getAllSchools());
        } else {
            model.addAttribute("transcripts", transcriptList);
            model.addAttribute("schoolList", schoolService.getAllSchools());
        }
        return "School/StudyRecord/addFormStudyRecord";
    }

    @PostMapping("/add-process")
    public String processAdd(@ModelAttribute StudyRecord studyRecord, Model model){
        // check study exist
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordByStudentAndSchoolAndSchoolYear(studyRecord.getStudent().getId(), studyRecord.getSchoolYear());
        if(studyRecordExist != null){
            model.addAttribute("Error", "Error, Study Existed !!!");
            model.addAttribute("studyRecord", new StudyRecord());
        } else {
            StudyRecord newStudy = new StudyRecord();
            newStudy.setResultConduct(studyRecord.getResultConduct());
            if(studyRecord.getSchool().getId() != 0 && studyRecord.getSchool() != null){
                newStudy.setSchool(studyRecord.getSchool());
            }
            newStudy.setSchoolYear(studyRecord.getSchoolYear());
            if(studyRecord.getStudent().getId()!= 0 && studyRecord.getStudent() != null){
                newStudy.setStudent(studyRecord.getStudent());
            }
            newStudy.setTranscriptList(studyRecord.getTranscriptList());
            newStudy.setCommentOfTeacher(studyRecord.getCommentOfTeacher());

            studyrecordService.addStudyRecord(newStudy);

            model.addAttribute("success", "You created new Study-record has id: "+ newStudy.getId());
            model.addAttribute("studyRecord", newStudy);
        }
        return "School/StudyRecord/notifySuccess";
    }

    @GetMapping("/showModifyFormStudyRecord")
    public String showModifyForm(@ModelAttribute StudyRecord studyRecord, Model model){
        // check study exist
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordById(studyRecord.getId());
        if(studyRecordExist == null){
            model.addAttribute("Error", "Error, Not found study record!!!");
            model.addAttribute("studyRecord", new StudyRecord());
        } else {
            model.addAttribute("studyRecord", studyRecordExist);
            // get list transcript
            List<Transcript> transcriptList = transcriptService.getAllTranscripts();
            if(transcriptList.isEmpty()){
                model.addAttribute("transcripts", new ArrayList<>());
            } else {
                model.addAttribute("transcripts", transcriptList);
            }
        }
        return "School/StudyRecord/modifyStudyRecord";
    }

    @PostMapping("/modify-process")
    public String processModify(@ModelAttribute StudyRecord studyRecord, Model model){
        // check study exist
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordById(studyRecord.getId());
        if(studyRecordExist == null){
            model.addAttribute("Error", "Error, Not found study record!!!");
            model.addAttribute("studyRecord", new StudyRecord());
        } else {
            studyRecordExist.setTranscriptList(studyRecord.getTranscriptList());
            studyRecordExist.setCommentOfTeacher(studyRecord.getCommentOfTeacher());
            studyRecordExist.setSchoolYear(studyRecord.getSchoolYear());
            studyRecordExist.setResultConduct(studyRecord.getResultConduct());
            studyrecordService.updateStudyRecord(studyRecordExist);
            model.addAttribute("success", "You modified a Study Record has Id: "+ studyRecordExist.getId());
            model.addAttribute("studyRecord", studyRecordExist);
            // get list transcript
            List<Transcript> transcriptList = transcriptService.getAllTranscripts();
            if(transcriptList.isEmpty()){
                model.addAttribute("transcripts", new ArrayList<>());
            } else {
                model.addAttribute("transcripts", transcriptList);
            }
        }
        return "School/StudyRecord/modifyStudyRecord";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute StudyRecord studyRecord, RedirectAttributes redirectAttributes){
        // check study exist
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordById(studyRecord.getId());
        if(studyRecordExist == null){
            redirectAttributes.addFlashAttribute("Error", "Error, Not found study record!!!");
        } else {
            studyrecordService.deleteStudyRecordById(studyRecordExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted a StudyRecord has ID: "+ studyRecordExist.getId());
        }
        return "redirect:/m-study/showManageStudyRecord";
    }
}
