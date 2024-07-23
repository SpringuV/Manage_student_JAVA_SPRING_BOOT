package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

public interface SubjectService {
    public List<Subject> getAllSubject();
    public Subject getSubjectById(int id);
    public Subject addSubject(Subject subject);
    public void deleteSubjectById(int id);
    public Subject updateSubject(Subject subject);
}
