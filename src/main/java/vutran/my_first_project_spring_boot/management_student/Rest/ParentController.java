package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.ParentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.ClassService;
import vutran.my_first_project_spring_boot.management_student.Service.ParentService;
import vutran.my_first_project_spring_boot.management_student.Service.SchoolService;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;
import vutran.my_first_project_spring_boot.management_student.Util.StringUtils;

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
    private ClassService classService;
    private ParentRepository parentRepository;

    @Autowired
    public ParentController(ParentRepository parentRepository, ParentService parentService, AuthorityRepository authorityRepository, SchoolService schoolService, StudentService studentService, ClassService classService) {
        this.parentService = parentService;
        this.authorityRepository = authorityRepository;
        this.schoolService = schoolService;
        this.studentService = studentService;
        this.classService = classService;
        this.parentRepository = parentRepository;
    }

    @GetMapping("/showManageParent")
    public String showParentList(Model model, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size){
        Page<Parent> parentList = parentRepository.findAll(PageRequest.of(page, size, Sort.by("firstName").ascending()));

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

    @GetMapping("/search-name")
    public String processSearch(@RequestParam("searchName") String searchName, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size, Model model){
        Page<Parent> parentPage = parentService.findParentsByFirstName(searchName, PageRequest.of(page, size, Sort.by("firstName").ascending()));
        // check list
        if(parentPage.isEmpty()){
            model.addAttribute("Error", "Error, Search not found Parent!!!");
            model.addAttribute("parentList", new ArrayList<>());
        } else {
            model.addAttribute("parentList", parentPage);
        }
        model.addAttribute("searchName", searchName);
        return "Parent/indexParent";
    }

    @GetMapping("showFormAddParent")
    public String addParentForm(Model model){
        model.addAttribute("parent", new Parent());
        model.addAttribute("schoolList", schoolService.getAllSchools());
        return "Parent/addParentForm";
    }

    @PostMapping("/add-process")
    public String addProcess(@ModelAttribute Parent parent, Model model){
        // check parent
        Parent parentExistUserName = parentService.getParentByUserName(parent.getUsername());
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
        newParent.setFirstName(StringUtils.formatString(parent.getFirstName()));
        newParent.setLastName(StringUtils.formatString(parent.getLastName()));
        newParent.setPosition("Parent");
        newParent.setUsername(parent.getUsername());
        newParent.setPassword(new BCryptPasswordEncoder().encode(parent.getPassword()));
        newParent.setEnabled(true);
        newParent.setEmail(parent.getEmail());
        if(parent.getSchool() != null && parent.getSchool().getId() != 0){
            newParent.setSchool(parent.getSchool());
        }
        if(parent.getStudent() != null && parent.getStudent().getId() != 0){
            newParent.setStudent(parent.getStudent());
        }
        if(parent.getClasses() != null && parent.getClasses().getId() != 0){
            newParent.setClasses(parent.getClasses());
        }
        newParent.setIdentity(parent.getIdentity());
        newParent.setAvatar(parent.getAvatar());
        newParent.setAddress(StringUtils.formatString(parent.getAddress()));
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
            parentExist.setAddress(StringUtils.formatString(parent.getAddress()));
            parentExist.setEmail(parent.getEmail());
            parentExist.setIdentity(parent.getIdentity());
            parentExist.setLastName(StringUtils.formatString(parent.getLastName()));
            if(parent.getSchool() != null && parent.getSchool().getId() != 0){
                parentExist.setSchool(parent.getSchool());
            }
            if(parent.getStudent() != null && parent.getStudent().getId() != 0){
                parentExist.setStudent(parent.getStudent());
            }
            if(parent.getClasses() != null && parent.getClasses().getId() != 0){
                parentExist.setClasses(parent.getClasses());
            }
            parentExist.setFirstName(StringUtils.formatString(parent.getFirstName()));
            parentExist.setPhoneNumber(parent.getPhoneNumber());
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
            return "redirect:/m-parent/showManageParent";
        } else {
            redirectAttributes.addFlashAttribute("Error", "Error process delete parent, null !!");
            return "redirect:/m-parent/showManageParent";
        }
    }
}