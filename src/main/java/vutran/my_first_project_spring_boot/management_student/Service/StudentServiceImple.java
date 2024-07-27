package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

@Service
public class StudentServiceImple implements StudentService{

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImple(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(int id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public List<Student> findALlStudentByPosition() {
        return studentRepository.findListStudentByPosition();
    }

    @Override
    public Student getStudentByUsername(String username) {
        return studentRepository.getStudentByUserName(username);
    }

    @Override
    public Student getStudentByIdentity(String identity) {
        return studentRepository.getStudentByIdentity(identity);
    }
}