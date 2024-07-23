package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Service.SubjectService;

import java.util.List;

@Controller
@RequestMapping("/api-subject")
public class SubjectController {

    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // get all
    @GetMapping
    public List<Subject> getAllSubject(){
        return subjectService.getAllSubject();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject){// tu dong bien json thanh students
        subject.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        subject = subjectService.addSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(subject);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<Subject> modifySubject(@PathVariable int id, @RequestBody Subject subject){
        Subject subjectExist = subjectService.getSubjectById(id);
        if(subjectExist != null){
            subjectExist.setNameSubject(subject.getNameSubject());
            subjectExist.setSchoolList(subject.getSchoolList());
            subjectExist.setTranscript(subject.getTranscript());
            subjectExist.setStudentList(subject.getStudentList());
            subjectExist.setTeacherList(subject.getTeacherList());
            subjectExist.setScoreCard(subject.getScoreCard());
            subjectService.updateSubject(subjectExist);
            return ResponseEntity.ok(subjectExist);
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
    public ResponseEntity<Subject> deleteSubjectById(@PathVariable int id){
        Subject subjectExist = subjectService.getSubjectById(id);
        if(subjectExist != null){
            subjectService.deleteSubjectById(id);
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
