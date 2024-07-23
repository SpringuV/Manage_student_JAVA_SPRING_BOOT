package vutran.my_first_project_spring_boot.management_student.Rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;
import vutran.my_first_project_spring_boot.management_student.Service.TranscriptService;

import java.util.List;

@Controller
@RequestMapping("/api-transript")
public class TranscriptController {
    private TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    // get all
    @GetMapping
    public List<Transcript> getAllTranscript(){
        return transcriptService.getAllTranscripts();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<Transcript> addTranscript(@RequestBody Transcript transcript){// tu dong bien json thanh students
        transcript.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        transcript = transcriptService.addTranscript(transcript);
        return ResponseEntity.status(HttpStatus.CREATED).body(transcript);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<Transcript> modifyTranscript(@PathVariable int id, @RequestBody Transcript transcript){
        Transcript transcriptExist = transcriptService.getTranscriptById(id);
        if(transcriptExist != null){
            transcriptExist.setScore(transcript.getScore());
            transcriptExist.setStudent(transcript.getStudent());
            transcriptExist.setSubject(transcript.getSubject());
            transcriptExist.setStudyRecord(transcript.getStudyRecord());
            transcriptExist.setSemester(transcript.getSemester());
            transcriptService.updateTranscript(transcriptExist);
            return ResponseEntity.ok(transcriptExist);
        } else {
            try {
                throw new Exception("Not found ScoreCard have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Transcript> deleteTranscriptById(@PathVariable int id){
        Transcript transcriptExist = transcriptService.getTranscriptById(id);
        if(transcriptExist != null){
            transcriptService.deleteTranscriptById(id);
            return ResponseEntity.ok().build();
        } else {
            try {
                throw new Exception("Not found parent have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
