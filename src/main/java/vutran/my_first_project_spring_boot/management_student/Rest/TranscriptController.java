package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.TranscriptRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/m-transcript")
public class TranscriptController {
    private TranscriptService transcriptService;
    private SchoolService schoolService;
    private TranscriptRepository transcriptRepository;
    private ClassService classService;
    private SubjectService subjectService;
    private StudentService studentService;
    private ScoreCardService scoreCardService;

    @Autowired
    public TranscriptController(ScoreCardService scoreCardService, StudentService studentService, SubjectService subjectService,ClassService classService, TranscriptRepository transcriptRepository,TranscriptService transcriptService, SchoolService schoolService) {
        this.transcriptService = transcriptService;
        this.schoolService = schoolService;
        this.transcriptRepository = transcriptRepository;
        this.classService = classService;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.scoreCardService = scoreCardService;
    }

    @GetMapping("/search-name")
    public String returnListTranscriptOfSchool(@RequestParam("searchNameOfSchool") String nameOfSchool, Model model, @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size){
        Page<Transcript> transcriptPage = transcriptService.getTranscriptBySchoolName(nameOfSchool, PageRequest.of(page, size, Sort.by("schoolYear").ascending()));
        if(transcriptPage.isEmpty()){
            model.addAttribute("Error", "Not found Name School !!!");
            model.addAttribute("transcripts", new ArrayList<>());
        } else {
            model.addAttribute("success", "Have "+ transcriptPage.getTotalElements()+" result.");
            model.addAttribute("transcripts", transcriptPage);
        }
        return "School/Transcript/indexTranscript";
    }

    @GetMapping("/showManageTranscript")
    public String showManage(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size) {
        Page<Transcript> transcriptList = transcriptRepository.findAll(PageRequest.of(page, size, Sort.by("schoolYear").ascending()));
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
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "School/Transcript/addFormTranscript";
    }

    @PostMapping("/add-process")
    public String addprocess(@ModelAttribute Transcript transcript, Model model) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptBySemesterAndSchoolYearAndName(transcript.getSemester(), transcript.getSchoolYear(), transcript.getSchool().getId(), transcript.getNameTranscript());
        if (transcriptExist != null) {
            model.addAttribute("Error", "Error, Transcript Existed !!!");
            model.addAttribute("schoolList", schoolService.getAllSchools());
            model.addAttribute("transcript", new Transcript());
        } else {
            // add transcript
            Transcript newTran = transcriptService.addTranscript(transcript);
            model.addAttribute("success", "You created new transcript have id: " + newTran.getId());
            model.addAttribute("schoolList", schoolService.getAllSchools());
        }
        return "School/Transcript/addFormTranscript";
    }

    @GetMapping("/showModifyFormTranscript")
    public String showFormModify(@ModelAttribute Transcript transcript, Model model) {
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptById(transcript.getId());
        if (transcriptExist != null) {
            model.addAttribute("transcript", transcriptExist);
            model.addAttribute("schoolList", schoolService.getAllSchools());
        } else {
            model.addAttribute("Error", "Transcript has id: " + transcript.getId() + " not existed !!!");
            model.addAttribute("transcript", new Transcript());
            model.addAttribute("schoolList", schoolService.getAllSchools());
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
            if(transcript.getSchool() != null){
                transcriptExist.setSchool(transcript.getSchool());
            }
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
            transcriptExist.getStudentSet().clear();
            transcriptExist.getStudyRecordList().clear();
            transcriptExist.getSubjectSet().clear();
            transcriptExist.setSchool(null);
            // update then delete
            transcriptService.updateTranscript(transcriptExist);
            transcriptService.deleteTranscriptById(transcriptExist.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted a transcript has id: " + transcript.getId());
        } else {
            redirectAttributes.addFlashAttribute("Error", "Transcript has id: " + transcript.getId() + " not existed !!!");
        }
        return "redirect:/m-transcript/showManageTranscript";
    }

    @GetMapping("/detailTranscript")
    public String showDetailForm(@RequestParam("id") int id, Model model){
        // check transcript exist
        Transcript transcriptExist = transcriptService.getTranscriptById(id);
        if (transcriptExist != null){
            List<Classes> classList = classService.getListClassByIdSchool(transcriptExist.getSchool().getId());
            model.addAttribute("transcript", transcriptExist);
            model.addAttribute("classList", classList);
        } else {
            model.addAttribute("Error", "Error, Not found Transcript !!!");
            return "redirect:/m-transcript/showManageTranscript";
        }
        return "School/Transcript/detailTranscript";
    }

    @PostMapping("/modify-saveScores")
    public String modifyDetail(@RequestParam("id_transcript") int id_transcript, @RequestParam Map<String, String> studentScores, RedirectAttributes redirectAttributes) throws Exception {
        // springboot tự động ánh xạ các giá trị trong thẻ input khi được đặt tên theo cú pháp hợp lệ
        // studentScores[1401][1], Spring sẽ gom tất cả các score lại trong một Map<String, String>

        // remove any entries that do not start with "studentScores", remove unrelated input tags
        studentScores.entrySet().removeIf(entry -> !entry.getKey().startsWith("studentScores"));

        System.out.println("Id: "+id_transcript);
        // check exist transcript
        Transcript transcriptExist = transcriptService.getTranscriptById(id_transcript);
        if(transcriptExist == null){
            throw new Exception("Not found Transcript has Id: "+ id_transcript);
        }
        // process transcript
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDate.format(formatter);
        System.out.println("date after formatted: "+ formattedDateTime);
            // convert LocalDate to java.util.date
            Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println("Name Transcript after formatted: "+transcriptExist.getNameTranscript().replace("_", " "));

        // Loop through the scores map and process each score
        for(Map.Entry<String, String> entry : studentScores.entrySet()){
            String key = entry.getKey();  // This will be something like "studentScores[1401][1]"
            String score = entry.getValue(); // The value entered in the form
            System.out.println("Key: "+ key+" Value: "+score);

            // Parse the student Id and subject Id from the key
            String[] ids = key.substring(key.indexOf('[')+1, key.length()-1).split("\\]\\[");
            try {
                int studentId = Integer.parseInt(ids[0]);
                int subjectId = Integer.parseInt(ids[1]);
                System.out.println("student id: "+studentId);
                System.out.println("subject id: "+subjectId);
                Student studentExist = studentService.getStudentById(studentId);
                Subject subjectExist = subjectService.getSubjectById(subjectId);
                if(studentExist == null){
                    throw new RuntimeException("Student Not Found !!!");
                } else if (subjectExist == null){
                    throw new RuntimeException("Subject Not Found !!!");
                }
                // create new scoreCard because a transcript have many scoreCards
                ScoreCard scoreCardNew = new ScoreCard();
                scoreCardNew.setTranscript(transcriptExist);
                scoreCardNew.setDayExam(new java.sql.Date(date.getTime()));
                scoreCardNew.setSchoolYear(transcriptExist.getSchoolYear());
                scoreCardNew.setNameExam(transcriptExist.getNameTranscript().replace("_", " "));
                scoreCardNew.setSchool(transcriptExist.getSchool());
                scoreCardNew.setStudent(studentExist);
                scoreCardNew.setSubject(subjectExist);
                scoreCardNew.setSemester(transcriptExist.getSemester());
                scoreCardNew.setScore(Double.parseDouble(score));
                // save score card
                scoreCardService.addScoreCard(scoreCardNew);
            } catch (Exception e){
                System.err.println("Invalid student or subject ID format: "+ key);
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("transcript", transcriptExist);
        redirectAttributes.addFlashAttribute("success", "All scores saved successfully !!!");

        // redirect to url with transcript id as a parameter
        return "redirect:/m-transcript/detailTranscript?id="+id_transcript;
    }
}

