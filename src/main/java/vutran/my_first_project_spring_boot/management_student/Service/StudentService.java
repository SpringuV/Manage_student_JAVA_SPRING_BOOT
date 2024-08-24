package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

public interface StudentService {
    public Student getStudentById(int id);
    public Student addStudent(Student student);
    public void deleteStudentById(int id);
    public Student updateStudent(Student student);
    public List<Student> findALlStudentByPosition();
    public Student getStudentByUsernameAndId(String username, int id);
    public Student getStudentByIdentity(String identity);
    public List<Student> getListStudentBySchoolId(int school_id);
    Student getStudentByUserName(String username);
    List<Student> getListStudentByClassAndSchool(int class_id, int school_id);
    List<Student> getListByClassId(int class_id);
}
