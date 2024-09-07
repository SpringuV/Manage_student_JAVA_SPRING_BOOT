package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

public interface StudentService {
    Student getStudentById(int id);
    Student addStudent(Student student);
    void deleteStudentById(int id);
    Student updateStudent(Student student);
    List<Student> findALlStudentByPosition();
    Student getStudentByUsernameAndId(String username, int id);
    Student getStudentByIdentity(String identity);
    List<Student> getListStudentBySchoolId(int school_id);
    Student getStudentByUserName(String username);
    List<Student> getListStudentByClassAndSchool(int class_id, int school_id);
    List<Student> getListByClassId(int class_id);
    Page<Student> findStudentsByFirstName(String searchName, PageRequest pageRequest);
}
