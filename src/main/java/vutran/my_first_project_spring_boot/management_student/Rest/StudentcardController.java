package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;
import vutran.my_first_project_spring_boot.management_student.Entity.StudentCard;
import vutran.my_first_project_spring_boot.management_student.Service.StudentcardService;

import java.util.List;

@Controller
@RequestMapping("/api-student-card")
public class StudentcardController {

    private StudentcardService studentcardService;

    @Autowired
    public StudentcardController(StudentcardService studentcardService) {
        this.studentcardService = studentcardService;
    }


    // get all
    @GetMapping
    public List<StudentCard> getAllStudentCard(){
        return studentcardService.getAllStudentCards();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<StudentCard> addStudentCard(@RequestBody StudentCard studentCard){// tu dong bien json thanh students
        studentCard.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        studentCard = studentcardService.addStudentCard(studentCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCard);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<StudentCard> modifyUpdateStudentCard(@PathVariable int id, @RequestBody StudentCard studentCard){
        StudentCard studentCardExist = studentcardService.getStudentCardById(id);
        if(studentCardExist != null){
            studentCardExist.setStudent(studentCard.getStudent());
            studentCardExist.setAvatar(studentCard.getAvatar());
            studentcardService.updateStudentCard(studentCardExist);
            return ResponseEntity.ok(studentCardExist);
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
    public ResponseEntity<StudentCard> deleteStudentCardById(@PathVariable int id){
        StudentCard studentCardExist = studentcardService.getStudentCardById(id);
        if(studentCardExist != null){
            studentcardService.deleteStudentCardById(id);
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
