package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentcardRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.StudentCard;

import java.util.List;

@Service
public class StudentcardServiceImple implements StudentcardService{

    private StudentcardRepository studentcardRepository;

    @Autowired
    public StudentcardServiceImple(StudentcardRepository studentcardRepository) {
        this.studentcardRepository = studentcardRepository;
    }

    @Override
    public List<StudentCard> getAllStudentCards() {
        return this.studentcardRepository.findAll();
    }

    @Override
    public StudentCard getStudentCardById(int id) {
        return this.studentcardRepository.findById(id).get();
    }

    @Override
    public StudentCard addStudentCard(StudentCard studentCard) {
        return this.studentcardRepository.save(studentCard);
    }

    @Override
    public void deleteStudentCardById(int id) {
        this.studentcardRepository.deleteById(id);
    }

    @Override
    public StudentCard updateStudentCard(StudentCard studentCard) {
        return this.studentcardRepository.saveAndFlush(studentCard);
    }
}
