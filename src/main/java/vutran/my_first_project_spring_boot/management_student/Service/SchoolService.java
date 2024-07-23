package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.School;

import java.util.List;

public interface SchoolService {
    public List<School> getAllSchools();
    public School getSchoolById(int id);
    public School addSchool(School school);
    public void deleteSchoolById(int id);
    public School updateSchool(School school);
    public List<School> findSchoolByNamePattern(String name);
}
