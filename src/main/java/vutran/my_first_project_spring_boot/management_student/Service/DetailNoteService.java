package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.DTO.NotebookDetailDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;

import java.util.Date;
import java.util.List;

public interface DetailNoteService {
    List<NoteBookDetail> getAllNoteBookDetail();
    NoteBookDetail getNoteBookDetailById(int id);
    NoteBookDetail addNoteBookDetail(NoteBookDetail noteBookDetail);
    void deleteNoteBookDetailById(int id);
    NoteBookDetail updateNoteBookDetail(NoteBookDetail noteBookDetail);
    List<NoteBookDetail> getDetailNoteByNoteBookId(int noteBookID);
    List<NoteBookDetail> getNoteBookDetailDuplicates(String time, int sub_id, Date teachDay);
    List<NotebookDetailDTO> getDetailNoteDTOByNoteBookId(int note_book_id);
    List<NotebookDetailDTO> getNoteDetailDTODuplicates(String time, int sub_id, Date dayTeach);
}
