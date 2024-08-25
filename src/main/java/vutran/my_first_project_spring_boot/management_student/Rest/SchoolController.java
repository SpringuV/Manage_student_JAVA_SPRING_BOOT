package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.SchoolRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m-school")
public class SchoolController {
    private SchoolService schoolService;
    private SubjectService subjectService;
    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository, SchoolService schoolService, SubjectService subjectService) {
        this.schoolService = schoolService;
        this.subjectService = subjectService;
        this.schoolRepository = schoolRepository;
    }

    @GetMapping("/getSchoolById/{schoolId}")
    @ResponseBody
    public School returnSchool(@PathVariable("schoolId") int id_school){
        return  schoolService.getSchoolById(id_school);
    }

    @GetMapping("/showManageSchool")
    public String showSchool(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size){
        // get school from database
        Page<School> schoolList = schoolRepository.findAll(PageRequest.of(page, size));
        // check size
        if (schoolList.isEmpty()){
            model.addAttribute("Error", "List School empty");
            model.addAttribute("schoolList", new ArrayList<>());
            return "School/indexSchool";
        }

        // forward to Form
        model.addAttribute("schoolList", schoolList);
        return "School/indexSchool";
    }

    @GetMapping("/showFormAddSchool")
    public String showFormAddSchool(Model model){
        School addSchool = new School();
        model.addAttribute("school", addSchool);
        return "School/addForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // add new school
    @PostMapping("/add-process")
    public String processAddSchool(@Valid @ModelAttribute School addSchool, BindingResult bindingResult, Model model, HttpSession httpSession){
        String schoolName = addSchool.getName();

        // form validation
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Error");
            return "School/addForm";
        }

        // check schoolExisted
        School schoolExisted = schoolService.findBySchoolName(schoolName);
        if(schoolExisted != null){
            model.addAttribute("school", new School());
            model.addAttribute("Error", "School Existed");
            return "School/addForm";
        }

        // school doesn't exist
        School school = new School();
        school.setName(addSchool.getName());
        school.setAddress(addSchool.getAddress());
        school.setPhone(addSchool.getPhone());
        // get subjectList
        List<Subject> subjectList = subjectService.getListSubjectBySchoolLevel(addSchool.getLevel());
        school.setSubjectList(subjectList);

        school.setLevel(addSchool.getLevel());
        schoolService.addSchool(school);

        // notify success
        model.addAttribute("success","You created new School has School Name: "+ school.getName());
        model.addAttribute("school", school);
        return "School/addForm";
    }

    // modify school
    @GetMapping("/showModifyForm")
    public String showModifyForm(@Valid @ModelAttribute School school, Model model){
        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool == null){
            model.addAttribute("school", new School());
            model.addAttribute("Error", "Not found school with id: "+ school.getId());
            return "School/indexSchool";
        }

        model.addAttribute("school", existSchool);
        return "School/modifyForm";
    }

    @PostMapping("/modify-process")
    public String processModify(@Valid @ModelAttribute School school, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("Error", "Errors");
            model.addAttribute("school", new School());
            return "School/modifyForm";
        }

        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool != null){
            existSchool.setName(school.getName());
            existSchool.setAddress(school.getAddress());
            existSchool.setPhone(school.getPhone());
            existSchool.setLevel(school.getLevel());
            // update subject
            List<Subject> subjectList = subjectService.getListSubjectBySchoolLevel(existSchool.getLevel());
            existSchool.setSubjectList(subjectList);
            // update school
            schoolService.updateSchool(existSchool);
        }
        model.addAttribute("success", "School updated successfully school with id " + existSchool.getId());
        model.addAttribute("school", existSchool);
        return "School/modifyForm";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@Valid @ModelAttribute School school , RedirectAttributes redirectAttributes){
        School existSchool = schoolService.getSchoolById(school.getId());
        if(existSchool != null){
            existSchool.getSubjectList().clear();
            existSchool.getClassesList().clear();
            existSchool.getNoteBookSet().clear();
            existSchool.getParentList().clear();
            existSchool.getScoreCardList().clear();
            existSchool.getStudentList().clear();
            existSchool.getStudyRecordList().clear();
            existSchool.getTeacherList().clear();
            existSchool.getTranscriptList().clear();
            // update and delete
            schoolService.updateSchool(existSchool);
            // delete
            schoolService.deleteSchoolById(existSchool.getId());
            List<School> schoolList = schoolService.getAllSchools();
            redirectAttributes.addAttribute("schoolList", schoolList);
            redirectAttributes.addAttribute("success","School deleted successfully school with id " + existSchool.getId());
            return "redirect:/m-school/showManageSchool";
        } else{
            List<School> schoolList = schoolService.getAllSchools();
            redirectAttributes.addAttribute("schoolList", schoolList);
            redirectAttributes.addAttribute("Error","Error process delete School, null !!");
            return "redirect:/m-school/showManageSchool";
        }
    }
}
