package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

public interface SubjectService {
    public List<Subject> getAllSubject();
    public Subject getSubjectById(int id);
    public void addSubject(Subject subject);
    public void deleteSubjectById(int id);
    public void updateSubject(Subject subject);
    public Subject getSubjectBySchoolIdAndName(int idSchool, String nameSubject);
    public List<Subject> getListSubjectOfSchoolId(int idSchool);

}
