package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;
import vutran.my_first_project_spring_boot.management_student.Entity.StudentCard;

import java.util.List;

public interface StudentcardService {
    public List<StudentCard> getAllStudentCards();
    public StudentCard getStudentCardById(int id);
    public StudentCard addStudentCard(StudentCard studentCard);
    public void deleteStudentCardById(int id);
    public StudentCard updateStudentCard(StudentCard studentCard);
}
