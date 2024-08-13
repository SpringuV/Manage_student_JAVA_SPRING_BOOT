package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;
import vutran.my_first_project_spring_boot.management_student.Entity.School;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;
import vutran.my_first_project_spring_boot.management_student.Service.ParentService;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/m-parent")
public class ParentController {

    private ParentService parentService;
    private AuthorityRepository authorityRepository;
    private SchoolService schoolService;
    private StudentService studentService;

    @Autowired
    public ParentController(ParentService parentService, AuthorityRepository authorityRepository, SchoolService schoolService, StudentService studentService) {
        this.parentService = parentService;
        this.authorityRepository = authorityRepository;
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping("/showManageParent")
    public String showParentList(Model model){
        List<Parent> parentList = parentService.findALlParentByPosition();

        // check list parent
        if(parentList.isEmpty()){
            model.addAttribute("Error", "List parent Error, Empty!!");
            model.addAttribute("parentList", new ArrayList<>());
            return "Parent/indexParent";
        } else{
            model.addAttribute("parentList", parentList);
            return "Parent/indexParent";
        }
    }

    @GetMapping("showFormAddParent")
    public String addParentForm(Model model){
        model.addAttribute("parent", new Parent());
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "Parent/addParentForm";
    }

    @GetMapping("/add-process")
    public String selectSchoolProcess(Model model, @RequestParam(value = "school", required = false) int id_school){
        model.addAttribute("parent", new Parent());
        // get List student
        List<Student> studentList = studentService.getListStudentBySchoolId(id_school);
        model.addAttribute("studentList", studentList);
        // get List school
        List<School> schoolList = schoolService.getAllSchools();
        model.addAttribute("schoolList", schoolList);
        return "Parent/addParentForm";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute Parent parent, Model model){
        // check parent
        Parent parentExistUserName = parentService.getParentByUsername(parent.getUsername());
        Parent parentExistIdentity = parentService.getParentByIdentity(parent.getIdentity());

        if(parentExistIdentity != null || parentExistUserName != null){
            model.addAttribute("Error", "Parent Existed");
            model.addAttribute("parent", new Parent());
            return "Parent/addParentForm";
        }
        // add parent
        Parent newParent = new Parent();
        newParent.setPhoneNumber(parent.getPhoneNumber());
        newParent.setId(parent.getId());
        newParent.setFirstName(parent.getFirstName());
        newParent.setLastName(parent.getLastName());
        newParent.setStudent(parent.getStudent());
        newParent.setPosition("Parent");
        newParent.setUsername(parent.getUsername());
        newParent.setPassword(new BCryptPasswordEncoder().encode(parent.getPassword()));
        newParent.setEnabled(true);
        newParent.setSchool(parent.getSchool());
        newParent.setEmail(parent.getEmail());
        newParent.setIdentity(parent.getIdentity());
        newParent.setAvatar(parent.getAvatar());
        newParent.setAddress(parent.getAddress());
        newParent.setStudent(parent.getStudent());
        // role
        Authority authority = authorityRepository.findByName("ROLE_PARENT");
        Collection<Authority> roles = new ArrayList<>();
        roles.add(authority);
        newParent.setCollectionAuthority(roles);
        // add
        parentService.addParent(newParent);
        model.addAttribute("success", "You created new parent have id: "+ newParent.getId()+" and Username: "+ newParent.getUsername());
        model.addAttribute("parent", newParent);
        return "Parent/addParentForm";
    }

    @GetMapping("/showModifyFormParent")
    public String showFormModifyParent(@ModelAttribute Parent parent, Model model){
        // check parent
        Parent parentExist = parentService.getParentById(parent.getId());
        if (parentExist == null){
            model.addAttribute("Error", "Not found Parent!!");
            model.addAttribute("schoolList", schoolService.getAllSchools());
            model.addAttribute("parent", new Parent());
            return "Parent/modifyParent";
        } else {
            model.addAttribute("parent", parentExist);
            model.addAttribute("schoolList", schoolService.getAllSchools());
            return "Parent/modifyParent";
        }
    }

    @PostMapping("/modify-process")
    public String processModifyParent(@ModelAttribute Parent parent, Model model){
        // check parent
        Parent parentExist = parentService.getParentById(parent.getId());
        if (parentExist == null){
            model.addAttribute("Error", "Not found Parent!!");
            model.addAttribute("parent", new Parent());
            return "Parent/modifyParent";
        } else {
            parentExist.setAddress(parent.getAddress());
            parentExist.setEmail(parent.getEmail());
            parentExist.setIdentity(parent.getIdentity());
            parentExist.setLastName(parent.getLastName());
            parentExist.setSchool(parent.getSchool());
            parentExist.setPhoneNumber(parent.getPhoneNumber());
            parentExist.setStudent(parent.getStudent());
            // tìm hiểu check pass encode
            parentService.updateParent(parentExist);
            model.addAttribute("parent", parentExist);
            model.addAttribute("success", "You modified parent have id: "+ parentExist.getId()+ " and UserName: "+parentExist.getUsername());
            return "Parent/modifyParent";
        }
    }

    @GetMapping("/modify-delete")
    public String deleteTeacher(@ModelAttribute Parent parent, RedirectAttributes redirectAttributes){
        Parent parentExist = parentService.getParentById(parent.getId());
        if(parentExist != null){
            parentService.deleteParentById(parentExist.getId());
            redirectAttributes.addFlashAttribute("success", "You have deleted parent have id: "+ parentExist.getId());
            List<Parent> parentList = parentService.findALlParentByPosition();
            redirectAttributes.addFlashAttribute("teacherList", parentList);
            return "redirect:/m-parent/showManageParent";
        } else {
            redirectAttributes.addFlashAttribute("Error", "Error process delete parent, null !!");
            List<Parent> parentList = parentService.findALlParentByPosition();
            redirectAttributes.addFlashAttribute("teacherList", parentList);
            return "redirect:/m-parent/showManageParent";
        }
    }
}