package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.List;

@Controller
@RequestMapping("/api-school")
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public List<School> getAllSchool(){
        return this.schoolService.getAllSchools();
    }

    @GetMapping("/find-name/{name}")
    public List<School> getSchoolName(@PathVariable String name){
        return schoolService.findSchoolByNamePattern(name);
    }

    @GetMapping("/find-id/{id}")
    public School getSchoolName(@PathVariable int id){
        return schoolService.getSchoolById(id);
    }

    // add student
    @PostMapping("/add")
    public ResponseEntity<School> addSchool(@RequestBody School school){ // tu dong bien json thanh students
        school.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        school = schoolService.addSchool(school);
        return ResponseEntity.status(HttpStatus.CREATED).body(school);
    }

    // modify school
    @PutMapping("/modify/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable int id, @RequestBody School school){
        School existSchool = schoolService.getSchoolById(id);
        if(existSchool != null) {
            existSchool.setName(school.getName());
            existSchool.setAddress(school.getAddress());
            existSchool.setPhone(school.getPhone());
            existSchool.setTeacherList(school.getTeacherList());
            existSchool.setClassesList(school.getClassesList());
            existSchool.setStudentList(school.getStudentList());
            existSchool.setSubjectList(school.getSubjectList());
            schoolService.updateSchool(existSchool);
            return ResponseEntity.ok(existSchool);
        } else {
            try {
                throw new Exception("Not found school have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // delete school
    @DeleteMapping("/modify/delete/{id}")
    public ResponseEntity<School> deleteSchoolByID(@PathVariable int id){
        School existSchool = schoolService.getSchoolById(id);
        if(existSchool != null) {
            schoolService.deleteSchoolById(id);
            return ResponseEntity.ok().build();
            // tim cach in ra error point
        } else {
            try {
                throw new Exception("Not found school have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
