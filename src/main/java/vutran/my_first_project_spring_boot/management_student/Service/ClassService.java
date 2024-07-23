package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

import java.util.List;

public interface ClassService {
    public List<Classes> getAllClasses();
    public Classes getClassById(int id);
    public Classes addClass(Classes classes);
    public void deleteClassById(int id);
    public Classes updateClass(Classes classes);


}
