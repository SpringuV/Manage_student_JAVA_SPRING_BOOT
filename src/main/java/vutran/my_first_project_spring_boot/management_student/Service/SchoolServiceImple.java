package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.SchoolRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.School;

import java.util.List;

@Service
@Transactional
public class SchoolServiceImple implements SchoolService{

    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImple(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<School> getAllSchools() {
        return this.schoolRepository.findAll();
    }

    @Override
    public School getSchoolById(int id) {
        return this.schoolRepository.findById(id).get();
    }

    @Override
    public School addSchool(School school) {
        return this.schoolRepository.save(school);
    }

    @Override
    public void deleteSchoolById(int id) {
        this.schoolRepository.deleteById(id);
    }

    @Override
    public School updateSchool(School school) {
        return this.schoolRepository.saveAndFlush(school);
    }

    @Override
    public List<School> findListSchoolByNamePattern(String name) {
        return this.schoolRepository.findByNameSchoolsPattern(name);
    }

    @Override
    public School findBySchoolName(String name) {
        return schoolRepository.findByNameSchool(name);
    }

    @Override
    public List<School> getListSchoolByLevel(String level) {
        return schoolRepository.getListSchoolByLevel(level);
    }
}
