package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.NotebookRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;

import java.util.List;

@Service
public class NotebookServiceImple implements NotebookService{

    private NotebookRepository notebookRepository;

    @Autowired
    public NotebookServiceImple(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    @Override
    public List<NoteBook> getAllNoteBooks() {
        return this.notebookRepository.findAll();
    }

    @Override
    public NoteBook getNoteBookById(int id) {
        return this.notebookRepository.findById(id).get();
    }

    @Override
    public NoteBook addNoteBook(NoteBook noteBook) {
        return this.notebookRepository.save(noteBook);
    }

    @Transactional // đảm bảo 'delete' nằm trong 1 session
    @Override
    public void deleteNoteBookById(int id) {
        notebookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public NoteBook updateNoteBook(NoteBook noteBook) {
        return this.notebookRepository.saveAndFlush(noteBook);
    }

    @Override
    public NoteBook findNoteBookByClassIdAndSchoolId(int class_id, int school) {
        return this.notebookRepository.findNoteBookByClassIdAndSchoolId(class_id, school);
    }
}
