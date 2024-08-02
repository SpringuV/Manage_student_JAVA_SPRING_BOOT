package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Service.NotebookService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;

import java.util.*;

@Controller
@RequestMapping("/m-note")
public class NotebookController {

    private NotebookService notebookService;
    private TeacherService teacherService;

    @Autowired
    public NotebookController(NotebookService notebookService, TeacherService teacherService) {
        this.notebookService = notebookService;
        this.teacherService = teacherService;
    }

    @GetMapping("/showManageNotebook")
    public String showListNote(Model model){
        List<NoteBook> noteBookList = notebookService.getAllNoteBooks();
        if(noteBookList.isEmpty()){
            model.addAttribute("Error", "Error, Notebook List Empty!!");
            model.addAttribute("teachers", new ArrayList<>());
            model.addAttribute("notebookList", new ArrayList<>());
            return "School/NoteBook/indexNotebook";
        } else{
            model.addAttribute("teachers", teacherService.getListTeacherByPosition());
            model.addAttribute("notebookList", noteBookList);
            return "School/NoteBook/indexNotebook";
        }
    }

    @GetMapping("/showFormAddNotebook")
    public String showForm(Model model){
        model.addAttribute("NoteBook", new NoteBook());
        model.addAttribute("teachers", teacherService.getListTeacherByPosition());
        return "School/NoteBook/addFormNoteBook";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute NoteBook noteBook, Model model){
        Set<Teacher> teacherSet = new HashSet<>();
        for(Teacher teacher: noteBook.getTeacherList()){
            Optional<Teacher> teacherOptional = teacherService.findById(teacher.getId());
            if(teacherOptional.isPresent()){
                teacherSet.add(teacherOptional.get());
            } else{
                model.addAttribute("Error", "Teacher with ID " + teacher.getId() + " not found.");
                model.addAttribute("NoteBook", new NoteBook());
                model.addAttribute("teachers", teacherService.getListTeacherByPosition());
                return "School/NoteBook/addFormNoteBook";
            }
        }
        // Check if note already exists
        NoteBook noteBookExist = notebookService.findNoteBookByClassIdAndSchoolId(noteBook.getClasses().getId(), noteBook.getSchool().getId());
        if(noteBookExist != null){
            model.addAttribute("Error", "Error, NoteBook Existed!!!");
            model.addAttribute("NoteBook", new NoteBook());
            model.addAttribute("teachers", teacherService.getListTeacherByPosition());
            return "School/NoteBook/addFormNoteBook";
        }
        noteBook.setTeacherList(teacherSet);
        notebookService.addNoteBook(noteBook);
        model.addAttribute("success", "You created new notebook have id: "+ noteBook.getId());
        model.addAttribute("NoteBook", noteBook);
        model.addAttribute("teachers", teacherService.getListTeacherByPosition());
        return "School/NoteBook/addFormNoteBook";
    }
}
