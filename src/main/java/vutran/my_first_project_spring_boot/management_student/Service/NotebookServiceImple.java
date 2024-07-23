package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public void deleteNoteBookById(int id) {
        this.notebookRepository.deleteById(id);
    }

    @Override
    public NoteBook updateNoteBook(NoteBook noteBook) {
        return this.notebookRepository.saveAndFlush(noteBook);
    }
}
