package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;
import vutran.my_first_project_spring_boot.management_student.Service.NotebookService;

import java.util.List;

@Controller
@RequestMapping("/api-note")
public class NotebookController {

    private final NotebookService notebookService;

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @GetMapping
    public List<NoteBook> showListNote(){
        return notebookService.getAllNoteBooks();
    }

    @GetMapping("/{id}")
    public NoteBook getNoteById(@PathVariable int id){
        return notebookService.getNoteBookById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<NoteBook> addNote(@RequestBody NoteBook noteBook){
        noteBook.setId(0);
        noteBook = notebookService.addNoteBook(noteBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteBook);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<NoteBook> updateNote(@PathVariable int id, @RequestBody NoteBook noteBook){
        NoteBook existNoteBook = notebookService.getNoteBookById(id);
        if(existNoteBook != null) {
            existNoteBook.setClasses(noteBook.getClasses());
            existNoteBook.setTeacherList(noteBook.getTeacherList());
            existNoteBook.setContentLecture(noteBook.getContentLecture());
            existNoteBook.setTeachingDay(noteBook.getTeachingDay());
            existNoteBook.setTeacherComment(noteBook.getTeacherComment());
            notebookService.updateNoteBook(existNoteBook);
            return ResponseEntity.ok(existNoteBook);
        } else {
            try {
                throw new Exception("Not found notebook have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NoteBook> deleteNoteBook(@PathVariable int id){
        NoteBook existNote = notebookService.getNoteBookById(id);
        if(existNote != null){
            notebookService.deleteNoteBookById(id);
            return ResponseEntity.ok().build();
        } else{
            try {
                throw new Exception("Not found notebook have id: "+ id);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

}
