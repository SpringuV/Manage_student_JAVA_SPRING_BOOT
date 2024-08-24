package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void addSubject(Subject subject) {
        this.subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public void deleteSubjectById(int id) {
        this.subjectRepository.deleteById(id);
    }

    @Override
    public void updateSubject(Subject subject) {
        this.subjectRepository.saveAndFlush(subject);
    }

    @Override
    public Subject getSubjectBySchoolIdAndName(int idSchool, String nameSubject) {
        return this.subjectRepository.getSubjectBySchoolIdAndName(idSchool, nameSubject);
    }

    @Override
    public List<Subject> getListSubjectOfSchoolId(int idSchool) {
        return this.subjectRepository.getListSubjectOfSchoolId(idSchool);
    }

    @Override
    public Subject getSubjectByName(String nameSubject) {
        return subjectRepository.getSubjectByName(nameSubject);
    }

    @Override
    public List<Subject> getListSubjectBySchoolLevel(String schoolLevel) {
        return subjectRepository.getListSubjectBySchoolLevel(schoolLevel);
    }
}
