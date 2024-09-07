package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubject();

    Subject getSubjectById(int id);

    void addSubject(Subject subject);

    void deleteSubjectById(int id);

    void updateSubject(Subject subject);

    Subject getSubjectBySchoolIdAndName(int idSchool, String nameSubject);

    List<Subject> getListSubjectOfSchoolId(int idSchool);

    Subject getSubjectByName(String nameSubject);

    List<Subject> getListSubjectBySchoolLevel(String schoolLevel);

    List<Subject> getSubjectByClassGrade(String grade);
}
