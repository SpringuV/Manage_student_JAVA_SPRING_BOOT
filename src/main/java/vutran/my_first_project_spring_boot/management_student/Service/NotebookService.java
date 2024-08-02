package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;

import java.util.List;

public interface NotebookService {
    public List<NoteBook> getAllNoteBooks();
    public NoteBook getNoteBookById(int id);
    public NoteBook addNoteBook(NoteBook noteBook);
    public void deleteNoteBookById(int id);
    public NoteBook updateNoteBook(NoteBook noteBook);
    public NoteBook findNoteBookByClassIdAndSchoolId(int class_id, int school);
}
