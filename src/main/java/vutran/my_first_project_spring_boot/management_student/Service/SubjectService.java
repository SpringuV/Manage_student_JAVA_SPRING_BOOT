package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO;
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
    List<SubjectDTO> getListSubjectDTOBySchoolLevel(String school_level);
    Subject getSubjectByName(String nameSubject);
    List<SubjectDTO> getListSubjectDTOBySchool(int school_id);
    List<Subject> getListSubjectBySchoolLevel(String schoolLevel);
    List<SubjectDTO> getListSubjectDTOByClassGrade(String class_grade);
    List<Subject> getSubjectByClassGrade(String grade);
}
