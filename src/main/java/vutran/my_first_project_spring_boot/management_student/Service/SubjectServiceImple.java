package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.SubjectRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

@Service
public class SubjectServiceImple implements SubjectService{

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImple(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubject() {
        return this.subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(int id) {
        return this.subjectRepository.findById(id).get();
    }

    @Override
    public Subject addSubject(Subject subject) {
        return this.subjectRepository.save(subject);
    }

    @Override
    public void deleteSubjectById(int id) {
        this.subjectRepository.deleteById(id);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return this.subjectRepository.saveAndFlush(subject);
    }
}
