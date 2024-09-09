package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.DTO.SchoolDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.School;

import java.util.List;

public interface SchoolService {
    List<School> getAllSchools();
    School getSchoolById(int id);
    School addSchool(School school);
    void deleteSchoolById(int id);
    School updateSchool(School school);
    List<School> findListSchoolByNamePattern(String name);
    School findBySchoolName(String name);
    List<School> getListSchoolByLevel(String level);
    List<SchoolDTO> getListSchoolDTOByLevel(String level);
}
