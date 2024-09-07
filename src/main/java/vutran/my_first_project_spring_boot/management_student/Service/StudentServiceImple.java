package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

@Service
@Transactional
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
    @Transactional
    public void deleteStudentById(int id) {
        Student student = studentRepository.getReferenceById(id);
        student.setClasses(null);
        student.setSchool(null);
        student.setTeacher(null);
        student.setParent(null);
        student.getTranscriptSet().clear();
        student.getStudyRecordList().clear();
        student.getSubjectList().clear();
        student.getScoreCardList().clear();
        studentRepository.saveAndFlush(student);
        studentRepository.deleteById(student.getId());
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
    public Student getStudentByUsernameAndId(String username, int id) {
        return studentRepository.getStudentByUserNameAndId(username, id);
    }

    @Override
    public Student getStudentByIdentity(String identity) {
        return studentRepository.getStudentByIdentity(identity);
    }

    @Override
    public List<Student> getListStudentBySchoolId(int school_id) {
        return studentRepository.getListStudentBySchoolId(school_id);
    }

    @Override
    public Student getStudentByUserName(String username) {
        return studentRepository.getStudentByUserName(username);
    }

    @Override
    public List<Student> getListStudentByClassAndSchool(int class_id, int school_id) {
        return studentRepository.getListStudentByClassAndSchool(class_id, school_id);
    }

    @Override
    public List<Student> getListByClassId(int class_id) {
        return studentRepository.getListByClassId(class_id);
    }

    @Override
    public Page<Student> findStudentsByFirstName(String searchName, PageRequest pageRequest) {
        return studentRepository.findStudentsByFirstName(searchName, pageRequest);
    }
}