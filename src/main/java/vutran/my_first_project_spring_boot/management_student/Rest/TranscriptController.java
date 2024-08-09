package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;
import vutran.my_first_project_spring_boot.management_student.Service.TranscriptService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-transcript")
public class TranscriptController {
    private TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @GetMapping("/showManageTranscript")
    public String showManage(Model model) {
        List<Transcript> transcriptList = transcriptService.getAllTranscripts();
        // check empty
        if (transcriptList.isEmpty()) {
            model.addAttribute("Error", "Error, List Transcript empty!!!");
            model.addAttribute("transcripts", new ArrayList<>());
        } else {
            model.addAttribute("transcripts", transcriptList);
        }
        return "School/Transcript/indexTranscript";
    }

    @GetMapping("/showFormAddTranscript")
    public String showForm(Model model) {
        model.addAttribute("transcript", new Transcript());
        return "School/Transcript/addFormTranscript";
    }

    @PostMapping("/add-process")
    public String addprocess(@ModelAttribute Transcript transcript, Model model) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptBySemesterAndSchoolYear(transcript.getSemester(), transcript.getSchoolYear());
        if (transcriptExist != null) {
            model.addAttribute("Error", "Error, Transcript Existed !!!");
            model.addAttribute("transcript", new Transcript());
        } else {
            // add transcript
            Transcript newTran = transcriptService.addTranscript(transcript);
            model.addAttribute("success", "You created new transcript have id: " + newTran.getId());
        }
        return "School/Transcript/addFormTranscript";
    }

    @GetMapping("/showModifyFormTranscript")
    public String showFormModify(@ModelAttribute Transcript transcript, Model model) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptById(transcript.getId());
        if (transcriptExist != null) {
            model.addAttribute("transcript", transcriptExist);
        } else {
            model.addAttribute("Error", "Transcript has id: " + transcript.getId() + " not existed !!!");
            model.addAttribute("transcript", new Transcript());
        }
        return "School/Transcript/modifyFormTranscript";
    }

    @PostMapping("/modify-process")
    public String modifyProcess(@ModelAttribute Transcript transcript, Model model) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptById(transcript.getId());
        if (transcriptExist != null) {
            transcriptExist.setNameTranscript(transcript.getNameTranscript());
            transcriptExist.setSemester(transcript.getSemester());
            transcriptExist.setSchool(transcript.getSchool());
            transcriptExist.setSchoolYear(transcript.getSchoolYear());
            transcriptService.updateTranscript(transcriptExist);
            model.addAttribute("success", "You modified transcript has id: " + transcriptExist.getId());
            model.addAttribute("transcript", transcriptExist);
        } else {
            model.addAttribute("Error", "Transcript has id: " + transcript.getId() + " not existed !!!");
            model.addAttribute("transcript", new Transcript());
        }
        return "School/Transcript/modifyFormTranscript";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute Transcript transcript, RedirectAttributes redirectAttributes) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptById(transcript.getId());
        if (transcriptExist != null) {
            transcriptService.deleteTranscriptById(transcriptExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted a transcript has id: " + transcript.getId());
        } else {
            redirectAttributes.addFlashAttribute("Error", "Transcript has id: " + transcript.getId() + " not existed !!!");
        }
        return "redirect:/m-transcript/showManageTranscript";
    }
}

