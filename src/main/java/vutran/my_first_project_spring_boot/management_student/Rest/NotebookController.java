package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vutran.my_first_project_spring_boot.management_student.Dao.NotebookRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;
import vutran.my_first_project_spring_boot.management_student.Service.NotebookService;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;

import java.util.*;

@Controller
@RequestMapping("/m-note")
public class NotebookController {

    private NotebookService notebookService;
    private TeacherService teacherService;
    private ClassService classService;
    private SchoolService schoolService;
    private NotebookRepository notebookRepository;

    @Autowired
    public NotebookController(NotebookRepository notebookRepository,NotebookService notebookService, TeacherService teacherService,
            ClassService classService, SchoolService schoolService) {
        this.notebookService = notebookService;
        this.teacherService = teacherService;
        this.classService = classService;
        this.schoolService = schoolService;
        this.notebookRepository = notebookRepository;
    }

    @GetMapping("/showManageNotebook")
    public String showListNote(Model model, @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size) {
        Page<NoteBook> noteBookList = notebookRepository.findAll(PageRequest.of(page, size));
        if (noteBookList.isEmpty()) {
            model.addAttribute("Error", "Error, Notebook List Empty!!");
            model.addAttribute("notebookList", new ArrayList<>());
            return "School/NoteBook/indexNotebook";
        } else {
            model.addAttribute("notebookList", noteBookList);
            return "School/NoteBook/indexNotebook";
        }
    }

    @GetMapping("/getClassBySchool/{schoolId}")
    @ResponseBody
    public List<Classes> returnListClass(@PathVariable("schoolId") int id_school) {
        return classService.getListClassByIdSchool(id_school);
    }

    @GetMapping("/showFormAddNotebook")
    public String showForm(Model model) {
        model.addAttribute("NoteBook", new NoteBook());
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "School/NoteBook/addFormNoteBook";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute NoteBook noteBook, Model model) {
        // Check if note already exists
        NoteBook noteBookExist = notebookService.findNoteBookByClassIdAndSchoolId(noteBook.getClasses().getId(),
                noteBook.getSchool().getId());
        if (noteBookExist != null) {
            model.addAttribute("Error", "Error, NoteBook Existed!!!");
            model.addAttribute("NoteBook", new NoteBook());
            model.addAttribute("teachers", teacherService.getListTeacherByPosition());
            return "School/NoteBook/addFormNoteBook";
        }
        notebookService.addNoteBook(noteBook);
        model.addAttribute("success", "You created new notebook have id: " + noteBook.getId());
        model.addAttribute("NoteBook", noteBook);
        return "School/NoteBook/addFormNoteBook";
    }

    @GetMapping("/showModifyFormNote")
    public String showFormModify(@ModelAttribute NoteBook noteBook, Model model) {
        // check note book exist
        NoteBook noteBookExist = notebookService.getNoteBookById(noteBook.getId());
        if (noteBookExist == null) {
            // notify error
            model.addAttribute("Error", "Not found NoteBook have id: " + noteBook.getId());
            model.addAttribute("schoolList", schoolService.getAllSchools());
            model.addAttribute("NoteBook", new NoteBook());
            return "School/NoteBook/modifyFormNotebook";
        }
        model.addAttribute("NoteBook", noteBookExist);
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "School/NoteBook/modifyFormNotebook";
    }

    @PostMapping("/modify-process")
    public String modifyProcess(@ModelAttribute NoteBook noteBook, Model model) {
        // Check if note already exists
        NoteBook noteBookExist = notebookService.findNoteBookByClassIdAndSchoolId(noteBook.getClasses().getId(),
                noteBook.getSchool().getId());
        if (noteBookExist == null) {
            model.addAttribute("Error", "Error, NoteBook Not Exist!!!");
            model.addAttribute("NoteBook", new NoteBook());
            return "School/NoteBook/modifyFormNotebook";
        }
        // modify
        noteBookExist.setClasses(noteBook.getClasses());
        noteBookExist.setSchool(noteBook.getClasses().getSchool());
        notebookService.updateNoteBook(noteBookExist);
        model.addAttribute("success", "You modified NoteBook have id: " + noteBookExist.getId());
        model.addAttribute("NoteBook", noteBookExist);
        return "School/NoteBook/modifyFormNotebook";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute NoteBook noteBook, RedirectAttributes redirectAttributes) {
        // Check if note already exists
        NoteBook noteBookExistWithId = notebookService.getNoteBookById(noteBook.getId());
        // exist
        if (noteBookExistWithId != null) {
            noteBookExistWithId.getNoteBookDetail().clear();
            noteBookExistWithId.setClasses(null);
            // update and delete
            notebookService.updateNoteBook(noteBookExistWithId);
            // delete
            notebookService.deleteNoteBookById(noteBookExistWithId.getId());
            redirectAttributes.addFlashAttribute("success",
                    "You deleted NoteBook have id: " + noteBookExistWithId.getId());
            return "redirect:/m-note/showManageNotebook";
        } else {
            redirectAttributes.addFlashAttribute("Error", "Error, NoteBook Not Exist!!!");
            return "redirect:/m-note/showManageNotebook";
        }
    }
}
