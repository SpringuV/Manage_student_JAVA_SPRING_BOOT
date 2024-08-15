package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface TeacherService {
    public Set<Teacher> getAllTeacher();
    public Teacher getTeacherById(int id);
    public Teacher addTeacher(Teacher teacher);
    public void deleteTeacherById(int id);
    public Teacher updateTeacher(Teacher teacher);
    public Teacher fineTeacherByUserName(String username);
    public Set<Teacher> getListTeacherByPosition();
    public Optional<Teacher> findById(int id);
    public List<Teacher> getListTeacherByIdSchool(int school_id);
    public List<Teacher> getListTeacherBySchoolIdAndClassID(int school_id, int class_id);
}
