package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Entity.Web.RegisterUser;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class EventFormController {

    private UserService userService;
    private AuthorityRepository authorityRepository;

    @Autowired
    public EventFormController(UserService userService, AuthorityRepository authorityRepository){
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    //homepage
    @GetMapping("/backHomepage")
    public String returnHomePage(){
        return "home";
    }

    // login
    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login";
    }

    //403
    @GetMapping("/showPage403")
    public String showPage403(){
        return "error/403";
    }

    // register
    @GetMapping("/register/showRegisterForm")
    public String showRegister(Model model){
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "Register/formRegister";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/register/process")
    public String process(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult, Model model, HttpSession httpSession){
    //@Valid: check chuan 100%, binding result trả về kết quả - thông báo, session lưu lại thông tin khi nhap sai...
        // validation user ?
        String username = registerUser.getUsername();
        // có lỗi thì redirect to register/form
        if(bindingResult.hasErrors()){
            return "Register/formRegister";
        }
        // check user existed
        User userCheck = userService.findUserByName(username);
        if(userCheck != null) {
            model.addAttribute("registerUser", new RegisterUser());
            model.addAttribute("myError", "Account existed");
            return "Register/formRegister";
        }

        // if doesn't exist
        // encode password by bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User userNew = new User();
        userNew.setUsername(registerUser.getUsername());
        userNew.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        userNew.setEnabled(true);
        userNew.setIdentity(registerUser.getIdentity());
        userNew.setLastName(registerUser.getLastName());
        userNew.setFirstName(registerUser.getFirstName());
        userNew.setEmail(registerUser.getEmail());
        userNew.setPosition(registerUser.getPosition());

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
        httpSession.setAttribute("myUser", userNew);
        return "Register/confirmation";
    }

}
