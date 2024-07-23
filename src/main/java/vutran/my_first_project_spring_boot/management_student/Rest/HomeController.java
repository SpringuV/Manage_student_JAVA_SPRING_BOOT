package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {



    @GetMapping()
    public String showHomePage(Model model){
        return "home";
    }
}
