package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.DetailNoteRepository;
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
    private DetailNoteRepository detailNoteRepository;

    @Autowired
    public DetailNoteBookController(DetailNoteRepository detailNoteRepository, DetailNoteService detailNoteService, NotebookService notebookService) {
        this.detailNoteService = detailNoteService;
        this.notebookService = notebookService;
        this.detailNoteRepository = detailNoteRepository;
    }

    @GetMapping("/showManageDetailNote")
    public String showManage(@RequestParam("id") int noteId, Model model, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size){
        // check note id is the valid
        if(noteId < 0){
            redirectAttributes.addFlashAttribute("Error", "Invalid NoteBook ID!");
            return "redirect:/m-note/showManageNotebook";
        }
        // check noteId exist
        NoteBook noteBookExist = notebookService.getNoteBookById(noteId);
        if(noteBookExist == null){
            redirectAttributes.addFlashAttribute("Error", "Not found NoteBook, processing detail note book !!!");
            return "redirect:/m-note/showManageNotebook";
        }
        Page<NoteBookDetail> noteBookDetailPage = detailNoteRepository.findAllByNotebookId(noteBookExist.getId(), PageRequest.of(page, size));
        // check list is empty
        if(noteBookDetailPage.isEmpty()){
            model.addAttribute("detailNoteList", new ArrayList<>());
            model.addAttribute("Error", "List Detail empty!!!");
            return "School/NoteBook/DetailNoteBook/indexDetailNote";
        }
        model.addAttribute("noteBook_id", noteBookExist.getId());
        model.addAttribute("detailNoteList", noteBookDetailPage);
        return "School/NoteBook/DetailNoteBook/indexDetailNote";
    }

    @GetMapping("/showFormAddNoteDetail")
    public String showFormAdd(NoteBook noteBook, Model model){
        NoteBook noteBookExist = notebookService.getNoteBookById(noteBook.getId());
        if(noteBookExist == null){
            model.addAttribute("Error", "Not found Note");
        }

        NoteBookDetail noteBookDetail = new NoteBookDetail();
        noteBookDetail.setNoteBook(noteBookExist); // set notebook
        model.addAttribute("noteDetail", noteBookDetail);
        return "School/NoteBook/DetailNoteBook/addFormDetailNote";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute NoteBookDetail noteBookDetail, Model model){
        System.out.println(noteBookDetail);
        // Check if note already exists - time, subject_id, teachday
//        List<NoteBookDetail> listExist = detailNoteService.getNoteBookDetailDuplicates(noteBookDetail.getTime(), noteBookDetail.getSubject().getId(), noteBookDetail.getTeachingDay());
//        if(listExist != null){
//            model.addAttribute("Error", "Error, NoteBookDetail existed !!!");
//            model.addAttribute("noteDetail", new NoteBookDetail());
//            return "School/NoteBook/DetailNoteBook/addFormDetailNote";
//        }
        NoteBook noteBookExist = notebookService.getNoteBookById(noteBookDetail.getNoteBook().getId());
        if(noteBookExist == null){
            model.addAttribute("Error", "Error, NoteBook not found !!!");
            model.addAttribute("noteDetail", new NoteBookDetail());
            return "School/NoteBook/DetailNoteBook/addFormDetailNote";
        }
        // valid
        NoteBookDetail newNoteBookDetail = new NoteBookDetail();
        newNoteBookDetail.setNoteBook(noteBookExist);
        System.out.println("NoteBook has id: "+ noteBookExist.getId());
        newNoteBookDetail.setTime(noteBookDetail.getTime());
        newNoteBookDetail.setSubject(noteBookDetail.getSubject());
        newNoteBookDetail.setTeacher(noteBookDetail.getTeacher());
        newNoteBookDetail.setTeachingDay(noteBookDetail.getTeachingDay());
        newNoteBookDetail.setContentLecture(noteBookDetail.getContentLecture());
        newNoteBookDetail.setTeacherComment(noteBookDetail.getTeacherComment());
        detailNoteService.addNoteBookDetail(newNoteBookDetail);
        model.addAttribute("success", "You created new notebook detail has id: "+ newNoteBookDetail.getId() +"of Class: "+ newNoteBookDetail.getNoteBook().getClasses().getName());
        model.addAttribute("noteDetail", newNoteBookDetail);
        return "School/NoteBook/DetailNoteBook/addFormDetailNote";
    }

    @GetMapping("/showModifyFormNoteDetail")
    public String showFormModify(@RequestParam("id") int note_detail_id, Model model){
        NoteBookDetail noteBookDetailExist = detailNoteService.getNoteBookDetailById(note_detail_id);
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
            model.addAttribute("noteDetail", new NoteBookDetail());
            return "School/NoteBook/DetailNoteBook/modifyFormDetailNote";
        }
        noteBookDetailExist.setTeacherComment(noteBookDetail.getTeacherComment());
        noteBookDetailExist.setTeacher(noteBookDetail.getTeacher());
        noteBookDetailExist.setContentLecture(noteBookDetail.getContentLecture());
        noteBookDetailExist.setTeachingDay(noteBookDetail.getTeachingDay());
        noteBookDetailExist.setTime(noteBookDetail.getTime());
        noteBookDetailExist.setSubject(noteBookDetail.getSubject());
        // update
        detailNoteService.updateNoteBookDetail(noteBookDetailExist);
        model.addAttribute("success", "You modified NoteBookDetail have id: " + noteBookDetailExist.getId());
        model.addAttribute("noteDetail", noteBookDetailExist);
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
