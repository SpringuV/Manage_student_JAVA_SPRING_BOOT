package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

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
    public String showLoginPage(@RequestParam(value = "expired", required = false) String expired){
        // Có thể kiểm tra xem tham số expired có tồn tại hay không
//        if (expired != null) {
//            // Xử lý logic nếu cần khi tham số expired tồn tại
//        }
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
        User registerUser = new User();
        model.addAttribute("registerUser", registerUser);
        return "Register/formRegister";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/register/process")
    public String process(@ModelAttribute User user, BindingResult bindingResult, Model model, HttpSession httpSession){
    //@Valid: check chuan 100%, binding result trả về kết quả - thông báo, session lưu lại thông tin khi nhap sai...
        // validation user ?
        String username = user.getUsername();
        // có lỗi thì redirect to register/form
        if(bindingResult.hasErrors()){
            return "Register/formRegister";
        }
        // check user existed
        User userCheck = userService.findUserByName(username);
        if(userCheck != null) {
            model.addAttribute("registerUser", new User());
            model.addAttribute("myError", "Account existed");
            return "Register/formRegister";
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
        userNew.setAvatar(user.getAvatar());
        userNew.setAddress(user.getAddress());
        userNew.setPhoneNumber(user.getPhoneNumber());

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
