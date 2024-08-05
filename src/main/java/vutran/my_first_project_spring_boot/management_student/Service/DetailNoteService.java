package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;

import java.util.Date;
import java.util.List;

public interface DetailNoteService {
    public List<NoteBookDetail> getAllNoteBookDetail();
    public NoteBookDetail getNoteBookDetailById(int id);
    public NoteBookDetail addNoteBookDetail(NoteBookDetail noteBookDetail);
    public void deleteNoteBookDetailById(int id);
    public NoteBookDetail updateNoteBookDetail(NoteBookDetail noteBookDetail);
    public List<NoteBookDetail> getDetailNoteByNoteBookId(int noteBookID);
    public List<NoteBookDetail> getNoteBookDetailDuplicates(String time, int sub_id, Date teachDay);
}
