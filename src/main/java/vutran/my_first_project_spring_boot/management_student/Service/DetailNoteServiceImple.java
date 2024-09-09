package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.DTO.NotebookDetailDTO;
import vutran.my_first_project_spring_boot.management_student.Dao.DetailNoteRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DetailNoteServiceImple implements DetailNoteService{

    private DetailNoteRepository detailNoteRepository;

    @Autowired
    public DetailNoteServiceImple(DetailNoteRepository detailNoteRepository) {
        this.detailNoteRepository = detailNoteRepository;
    }

    @Override
    public List<NoteBookDetail> getAllNoteBookDetail() {
        return detailNoteRepository.findAll();
    }

    @Override
    public NoteBookDetail getNoteBookDetailById(int id) {
        return detailNoteRepository.findById(id).get();
    }

    @Override
    public NoteBookDetail addNoteBookDetail(NoteBookDetail noteBookDetail) {
        return detailNoteRepository.save(noteBookDetail);
    }

    @Override
    public void deleteNoteBookDetailById(int id) {
        this.detailNoteRepository.deleteById(id);
    }

    @Override
    public NoteBookDetail updateNoteBookDetail(NoteBookDetail noteBookDetail) {
        return detailNoteRepository.saveAndFlush(noteBookDetail);
    }

    @Override
    public List<NoteBookDetail> getDetailNoteByNoteBookId(int noteBookID) {
        return detailNoteRepository.getDetailNoteByNoteBookId(noteBookID);
    }

    @Override
    public List<NoteBookDetail> getNoteBookDetailDuplicates(String time, int sub_id, Date teachDay) {
        return detailNoteRepository.getNoteBookDetailDuplicates(time, sub_id, teachDay);
    }

    @Override
    public List<NotebookDetailDTO> getDetailNoteDTOByNoteBookId(int note_book_id) {
        return detailNoteRepository.getDetailNoteDTOByNoteBookId(note_book_id);
    }

    @Override
    public List<NotebookDetailDTO> getNoteDetailDTODuplicates(String time, int sub_id, Date dayTeach) {
        return detailNoteRepository.getNoteDetailDTODuplicates(time, sub_id, dayTeach);
    }

}
