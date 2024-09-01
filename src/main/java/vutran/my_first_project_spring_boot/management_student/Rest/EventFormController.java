package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // after login success return to home
    @GetMapping()
    public String showHomePage(Model model){
        return "home";
    }

    //homepage
    @GetMapping("/backHomepage")
    public String returnHomePage(){
        return "home";
    }

    // login
    @GetMapping("/showLoginPage")
    public String showLoginPage(@RequestParam(value = "expired", required = false) String expired, Model model){
        // Có thể kiểm tra xem tham số expired có tồn tại hay không
        if (expired != null) {
            // Xử lý logic nếu cần khi tham số expired tồn tại
            model.addAttribute("error", "Your session has expired. Please log in again.");
        }
        return "login";
    }

    //403
    @GetMapping("/showPage403")
    public String showPage403(){
        return "error/403";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
