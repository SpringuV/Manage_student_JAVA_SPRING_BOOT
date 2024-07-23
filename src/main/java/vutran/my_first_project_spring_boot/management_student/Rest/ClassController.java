package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;

import java.util.List;

@Controller
@RequestMapping("/api-classes")
public class ClassController {
    private ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<Classes> showListNote(){
        return classService.getAllClasses();
    }

    @GetMapping("/{id}")
    public Classes getClassById(@PathVariable int id){
        return classService.getClassById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Classes> addNote(Classes classes){
        classes.setId(0);
        classes = classService.addClass(classes);
        return ResponseEntity.status(HttpStatus.CREATED).body(classes);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Classes> updateClass(@PathVariable int id, @RequestBody Classes classes){
        Classes existClasses = classService.getClassById(id);
        if(existClasses != null) {
            existClasses.setName(classes.getName());
            existClasses.setGrade(classes.getGrade());
            existClasses.setTeacherList(classes.getTeacherList());
            existClasses.setSchool(classes.getSchool());
            existClasses.setNoteBook(classes.getNoteBook());
            existClasses.setStudentList(classes.getStudentList());
            classService.updateClass(existClasses);
            return ResponseEntity.ok(existClasses);
        } else {
            try {
                throw new Exception("Not found classes have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Classes> deleteClasses(@PathVariable int id){
        Classes existClasses = classService.getClassById(id);
        if(existClasses != null){
            classService.deleteClassById(id);
            return ResponseEntity.ok().build();
        } else{
            try {
                throw new Exception("Not found classes have id: "+ id);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
