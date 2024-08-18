package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Service.DetailNoteService;
import vutran.my_first_project_spring_boot.management_student.Service.NotebookService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-note-detail")
public class DetailNoteBookController {

    private DetailNoteService detailNoteService;
    private NotebookService notebookService;

    @Autowired
    public DetailNoteBookController(DetailNoteService detailNoteService, NotebookService notebookService) {
        this.detailNoteService = detailNoteService;
        this.notebookService = notebookService;
    }

    @GetMapping("/showManageDetailNote")
    public String showManage(@ModelAttribute NoteBook noteBook, Model model, RedirectAttributes redirectAttributes){
        // check note id is the valid
        if(noteBook.getId() < 0){
            redirectAttributes.addFlashAttribute("Error", "Invalid NoteBook ID!");
            return "redirect:/m-note/showManageNotebook";
        }
        // check noteId exist
        NoteBook noteBookExist = notebookService.getNoteBookById(noteBook.getId());
        if(noteBookExist == null){
            redirectAttributes.addFlashAttribute("Error", "Not found NoteBook, processing detail note book !!!");
            return "redirect:/m-note/showManageNotebook";
        }
        List<NoteBookDetail> noteBookDetailList = detailNoteService.getDetailNoteByNoteBookId(noteBookExist.getId());
        // check list is empty
        if(noteBookDetailList.isEmpty()){
            model.addAttribute("detailNoteList", new ArrayList<>());
            // save idNoteBook
            model.addAttribute("noteBook_id", noteBookExist.getId());
            model.addAttribute("Error", "List Detail empty!!!");
            return "School/NoteBook/DetailNoteBook/indexDetailNote";
        }
        // save idNoteBook
        model.addAttribute("noteBook_id", noteBookExist.getId());
        model.addAttribute("detailNoteList", noteBookDetailList);
        return "School/NoteBook/DetailNoteBook/indexDetailNote";
    }

    @GetMapping("/showFormAddNoteDetail")
    public String showFormAdd(NoteBook noteBook, Model model){
        model.addAttribute("noteDetail", new NoteBookDetail());
        List<Teacher> teacherList = noteBook.getClasses().getTeacherList();
        List<Subject> subjectList = noteBook.getSchool().getSubjectList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("noteBook_id", noteBook.getId());
        return "School/NoteBook/DetailNoteBook/addFormDetailNote";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute NoteBookDetail noteBookDetail, Model model){
        // Check if note already exists - time, subject_id, teachday
        List<NoteBookDetail> listExist = detailNoteService.getNoteBookDetailDuplicates(noteBookDetail.getTime(), noteBookDetail.getSubject().getId(), noteBookDetail.getTeachingDay());
        if(listExist != null){
            model.addAttribute("Error", "Error, NoteBookDetail existed !!!");
            model.addAttribute("noteDetail", new NoteBookDetail());
            return "School/NoteBook/DetailNoteBook/addFormDetailNote";
        }
        // valid
        NoteBookDetail newNoteBookDetail = detailNoteService.addNoteBookDetail(noteBookDetail);
        model.addAttribute("success", "You created new notebook detail have id: "+ newNoteBookDetail.getId());
        model.addAttribute("noteDetail", newNoteBookDetail);
        return "School/NoteBook/DetailNoteBook/addFormDetailNote";
    }

    @GetMapping("/showModifyFormNoteDetail")
    public String showFormModify(@ModelAttribute NoteBookDetail  noteBookDetail, Model model){
        NoteBookDetail noteBookDetailExist = detailNoteService.getNoteBookDetailById(noteBookDetail.getId());
        // if exist
        if(noteBookDetailExist != null){
            model.addAttribute("noteDetail", noteBookDetailExist);
            return "School/NoteBook/DetailNoteBook/modifyFormDetailNote";
        } else {
            model.addAttribute("Error", "Error, DetailNote Not Found !!!");
            model.addAttribute("noteDetail", new NoteBookDetail());
            return "School/NoteBook/DetailNoteBook/modifyFormDetailNote";
        }
    }

    @PostMapping("/modify-process")
    public String modifyProcess(@ModelAttribute NoteBookDetail noteBookDetail, Model model){
        // Check if note already exists
        NoteBookDetail noteBookDetailExist = detailNoteService.getNoteBookDetailById(noteBookDetail.getId());
        if(noteBookDetailExist == null){
            model.addAttribute("Error", "Error, NoteBookDetail Not Exist!!!");
            model.addAttribute("NoteBook", new NoteBook());
            return "School/NoteBook/modifyFormNotebook";
        }
        noteBookDetailExist.setTeacherComment(noteBookDetail.getTeacherComment());
        noteBookDetailExist.setTeacher(noteBookDetail.getTeacher());
        noteBookDetailExist.setContentLecture(noteBookDetail.getContentLecture());
        noteBookDetailExist.setTeachingDay(noteBookDetail.getTeachingDay());
        noteBookDetailExist.setTime(noteBookDetail.getTime());
        noteBookDetailExist.setSubject(noteBookDetail.getSubject());
        detailNoteService.updateNoteBookDetail(noteBookDetailExist);
        model.addAttribute("success", "You modified NoteBookDetail have id: " + noteBookDetailExist.getId());
        model.addAttribute("NoteBook", noteBookDetailExist);
        return "School/NoteBook/DetailNoteBook/modifyFormDetailNote";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute NoteBookDetail noteBookDetail, RedirectAttributes redirectAttributes){
        // Check if notedetail already exists
        NoteBookDetail noteBookExistWithId = detailNoteService.getNoteBookDetailById(noteBookDetail.getId());
        //exist
        if(noteBookExistWithId != null){
            detailNoteService.deleteNoteBookDetailById(noteBookExistWithId.getId());
            redirectAttributes.addFlashAttribute("success", "You deleted NoteBookDetail have id: "+ noteBookExistWithId.getId());
            return "redirect:/m-note-detail/showManageDetailNote";
        } else{
            redirectAttributes.addFlashAttribute("Error", "Error, NoteBookDetail Not Exist!!!");
            return "redirect:/m-note-detail/showManageDetailNote";
        }
    }
}
