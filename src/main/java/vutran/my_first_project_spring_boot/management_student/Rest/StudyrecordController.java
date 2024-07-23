package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;
import vutran.my_first_project_spring_boot.management_student.Service.StudyrecordService;

import java.util.List;

@Controller
@RequestMapping("/api-study-record")
public class StudyrecordController {
    private StudyrecordService studyrecordService;

    @Autowired
    public StudyrecordController(StudyrecordService studyrecordService) {
        this.studyrecordService = studyrecordService;
    }

    // get all
    @GetMapping
    public List<StudyRecord> getAllStudyRecord(){
        return studyrecordService.getAllStudyRecords();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<StudyRecord> addStudyRecord(@RequestBody StudyRecord studyRecord){// tu dong bien json thanh students
        studyRecord.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        studyRecord = studyrecordService.addStudyRecord(studyRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(studyRecord);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<StudyRecord> modifyStudyRecord(@PathVariable int id, @RequestBody StudyRecord studyRecord){
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordById(id);
        if(studyRecordExist != null){
            studyRecordExist.setStudent(studyRecord.getStudent());
            studyRecordExist.setCommentOfParent(studyRecord.getCommentOfParent());
            studyRecordExist.setCommentOfTeacher(studyRecord.getCommentOfTeacher());
            studyRecordExist.setResultConduct(studyRecord.getResultConduct());
            studyRecordExist.setTranscriptList(studyRecord.getTranscriptList());
            studyrecordService.updateStudyRecord(studyRecordExist);
            return ResponseEntity.ok(studyRecordExist);
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
    public ResponseEntity<StudyRecord> deleteStudyRecordById(@PathVariable int id){
        StudyRecord studyRecordExist = studyrecordService.getStudyRecordById(id);
        if(studyRecordExist != null){
            studyrecordService.deleteStudyRecordById(id);
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
