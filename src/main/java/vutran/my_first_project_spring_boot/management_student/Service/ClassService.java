package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

import java.util.List;

public interface ClassService {
    List<Classes> getAllClasses();

    Classes getClassById(int id);

    Classes addClass(Classes classes);

    void deleteClassById(int id);

    Classes updateClass(Classes classes);

    Classes findClassByNameAndSchoolId(String name, int id);

    List<Classes> getListClassByIdSchool(int school_id);

    Classes getClassByStudentAndSchool(int student_id, int school_id);

    String getGrade(int class_id);
}
