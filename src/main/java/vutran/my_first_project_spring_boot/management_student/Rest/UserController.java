package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.ParentService;
import vutran.my_first_project_spring_boot.management_student.Service.StudentService;
import vutran.my_first_project_spring_boot.management_student.Service.TeacherService;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/m-user")
public class UserController {
    private UserRepository userRepository;
    private UserService userService;
    private AuthorityRepository authorityRepository;
    private ParentService parentService;
    private TeacherService teacherService;
    private StudentService studentService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, AuthorityRepository authorityRepository, ParentService parentService, StudentService studentService, TeacherService teacherService) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.parentService = parentService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/showManageUser")
    public String showListUser(Model model){
        List<User> userList = userService.getAllUser();
        // check List
        if(userList.isEmpty()){
            model.addAttribute("Error", "Error, List User Empty!!");
            model.addAttribute("userList", new ArrayList<>());
        }
        model.addAttribute("userList", userList);
        return "User/indexUser";
    }

    @GetMapping("/showFormAddUser")
    public String showFormAddUser(Model model){
        model.addAttribute("user", new User());
        return "User/addFormUser";
    }

    @GetMapping("/showModifyFormUser")
    public String showModifyUser(@ModelAttribute User user, Model model){
        // check user exist
        User userExist = userService.getUserById(user.getId());
        if(userExist == null){
            model.addAttribute("Error", "Not found User");
            model.addAttribute("user", new User());
        } else{
            model.addAttribute("user", userExist);
        }
        return "User/modifyFormUser";
    }

    @PostMapping("/add-process")
    public String addProcessUser(@ModelAttribute User user, BindingResult bindingResult, Model model){
        String username = user.getUsername();
        String identity = user.getIdentity();
        // có lỗi thì redirect to register/form
        if(bindingResult.hasErrors()){
            return "User/addFormUser";
        }
        // check user existed
        User userCheckUserName = userService.findUserByName(username);
        User userCheckIdentity = userService.findUserByIdentity(identity);
        if(userCheckUserName != null || userCheckIdentity != null) {
            model.addAttribute("user", new User());
            model.addAttribute("Error", "Account existed");
            return "User/addFormUser";
        }
        Authority defaultRole = new Authority();
        String position = user.getPosition();
        if(position.equals("Teacher")){
            defaultRole = authorityRepository.findByName("ROLE_TEACHER");
            Teacher teacherNew = new Teacher();
            // encode password by bcrypt
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            teacherNew.setUsername(user.getUsername());
            teacherNew.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            teacherNew.setEnabled(true);
            teacherNew.setAddress(user.getAddress());
            teacherNew.setIdentity(user.getIdentity());
            teacherNew.setLastName(user.getLastName());
            teacherNew.setFirstName(user.getFirstName());
            teacherNew.setEmail(user.getEmail());
            teacherNew.setPosition(user.getPosition());
            Collection<Authority> roles = new ArrayList<>();
            roles.add(defaultRole);
            teacherNew.setCollectionAuthority(roles);
            teacherService.addTeacher(teacherNew);
            // notify success
            model.addAttribute("success", "You created new User have id: "+ teacherNew.getId()+" Name: "+teacherNew.getFirstName());
            model.addAttribute("user", teacherNew);

        } else if (position.equals("User")) {
            defaultRole = authorityRepository.findByName("ROLE_USER");
            // if doesn't exist
            // encode password by bcrypt
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User userNew = new User();
            userNew.setUsername(user.getUsername());
            userNew.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userNew.setEnabled(true);
            userNew.setAddress(user.getAddress());
            userNew.setIdentity(user.getIdentity());
            userNew.setLastName(user.getLastName());
            userNew.setFirstName(user.getFirstName());
            userNew.setEmail(user.getEmail());
            userNew.setPosition(user.getPosition());

            // create authority
            Collection<Authority> roles = new ArrayList<>();
            roles.add(defaultRole);
            userNew.setCollectionAuthority(roles);

            userService.addUser(userNew);
            // notify success
            model.addAttribute("success", "You created new User have id: "+ userNew.getId()+" Name: "+userNew.getFirstName());
            model.addAttribute("user", userNew);
        } else if (position.equals("Parent")) {
            defaultRole = authorityRepository.findByName("ROLE_PARENT");
            // if doesn't exist
            // encode password by bcrypt
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            Parent newParent = new Parent();
            newParent.setUsername(user.getUsername());
            newParent.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            newParent.setEnabled(true);
            newParent.setAddress(user.getAddress());
            newParent.setIdentity(user.getIdentity());
            newParent.setLastName(user.getLastName());
            newParent.setFirstName(user.getFirstName());
            newParent.setEmail(user.getEmail());
            newParent.setPosition(user.getPosition());

            // create authority
            Collection<Authority> roles = new ArrayList<>();
            roles.add(defaultRole);
            newParent.setCollectionAuthority(roles);

            parentService.addParent(newParent);
            // notify success
            model.addAttribute("success", "You created new User have id: "+ newParent.getId()+" Name: "+newParent.getFirstName());
            model.addAttribute("user", newParent);
        } else if (position.equals("Student")) {
            defaultRole = authorityRepository.findByName("ROLE_STUDENT");
            // if doesn't exist
            // encode password by bcrypt
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            Student newStudent = new Student();
            newStudent.setUsername(user.getUsername());
            newStudent.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            newStudent.setEnabled(true);
            newStudent.setAddress(user.getAddress());
            newStudent.setIdentity(user.getIdentity());
            newStudent.setLastName(user.getLastName());
            newStudent.setFirstName(user.getFirstName());
            newStudent.setEmail(user.getEmail());
            newStudent.setPosition(user.getPosition());

            // create authority
            Collection<Authority> roles = new ArrayList<>();
            roles.add(defaultRole);
            newStudent.setCollectionAuthority(roles);

            studentService.addStudent(newStudent);
            // notify success
            model.addAttribute("success", "You created new User have id: "+ newStudent.getId()+" Name: "+newStudent.getFirstName());
            model.addAttribute("user", newStudent);
        }
        return "User/addFormUser";
    }

    @PostMapping("/modify-process")
    public String processModify(@ModelAttribute User user, Model model){
        // check user
        User userExist = userService.getUserById(user.getId());
        if(userExist == null){
            model.addAttribute("Error", "Not found User");
            model.addAttribute("user", new User());
            return "User/modifyFormUser";
        }
        // update user
        userExist.setFirstName(user.getFirstName());
        userExist.setAddress(user.getAddress());
        userExist.setIdentity(user.getIdentity());
        userExist.setPhoneNumber(user.getPhoneNumber());
        userExist.setLastName(user.getLastName());
        userExist.setEmail(user.getEmail());
        userService.updateUser(userExist);
        model.addAttribute("success", "Modified User have id: "+ userExist.getId()+" Name: "+userExist.getFirstName());
        model.addAttribute("user", userExist);
        return "User/modifyFormUser";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        // check user
        User userExist = userService.getUserById(user.getId());
        if(userExist == null){
            redirectAttributes.addFlashAttribute("Error", "Not found User");
            return "redirect:/m-user/showManageUser";
        } else{
            userService.deleteUserById(userExist.getId());
            redirectAttributes.addFlashAttribute("success", "Deleted user have id: "+userExist.getId());
            List<User> userList = userService.getAllUser();
            redirectAttributes.addFlashAttribute("userList", userList);
            return "redirect:/m-user/showManageUser";
        }
    }
}
