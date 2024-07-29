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
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Entity.Web.RegisterUser;
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

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
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

    @GetMapping("/showAddFormUser")
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

        // if doesn't exist
        // encode password by bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User userNew = new User();
        userNew.setUsername(user.getUsername());
        userNew.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userNew.setEnabled(true);
        userNew.setIdentity(user.getIdentity());
        userNew.setLastName(user.getLastName());
        userNew.setFirstName(user.getFirstName());
        userNew.setEmail(user.getEmail());
        userNew.setPosition(user.getPosition());

        // create authority
        Authority defaultRole = new Authority();
        if(userNew.getPosition().equals("Teacher")){
            defaultRole = authorityRepository.findByName("ROLE_TEACHER");
        } else if (userNew.getPosition().equals("User")) {
            defaultRole = authorityRepository.findByName("ROLE_USER");
        } else if (userNew.getPosition().equals("Parent")) {
            defaultRole = authorityRepository.findByName("ROLE_PARENT");
        } else if (userNew.getPosition().equals("Student")) {
            defaultRole = authorityRepository.findByName("ROLE_STUDENT");
        }
        Collection<Authority> roles = new ArrayList<>();
        roles.add(defaultRole);
        userNew.setCollectionAuthority(roles);

        userService.addUser(userNew);

        // notify success
        model.addAttribute("user", userNew);
        return "Register/confirmation";
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
}
