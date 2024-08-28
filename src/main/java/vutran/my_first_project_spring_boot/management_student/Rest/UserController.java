package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/m-user")
public class UserController {
    private UserService userService;
    private AuthorityRepository authorityRepository;
    private ParentService parentService;
    private TeacherService teacherService;
    private StudentService studentService;
    private UserRepository userRepository;
    private SchoolService schoolService;

    @Autowired
    public UserController(SchoolService schoolService, UserService userService, AuthorityRepository authorityRepository, ParentService parentService, StudentService studentService, TeacherService teacherService, UserRepository userRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.parentService = parentService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.schoolService = schoolService;
    }

    @GetMapping("/showManageUser")
    public String showListUser(Model model, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "15") int size){
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        // Necessary update if insert by mysql workbench
        //        updateRole();
        // check List
        if(userPage.isEmpty()){
            model.addAttribute("Error", "Error, List User Empty!!");
            model.addAttribute("userList", new ArrayList<>());
        }
        model.addAttribute("userList", userPage);
        return "User/indexUser";
    }

    // search user by name
    @GetMapping("/search-name")
    public String processSearch(@RequestParam("searchName") String searchName, Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size){

        Page<User> userPage = userService.getListUserByFirstName(searchName, PageRequest.of(page, size));
        // check List
        if(userPage.isEmpty()){
            model.addAttribute("Error", "Error, Not found User!!!");
            model.addAttribute("userList", new PageImpl<>(new ArrayList<>(), PageRequest.of(page, size), 0));
        } else {
            model.addAttribute("userList", userPage);
        }
        model.addAttribute("searchName", searchName);
        return "User/indexUser";
    }

    @GetMapping("/showFormAddUser")
    public String showFormAddUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("schoolList", schoolService.getAllSchools());
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
        switch (position) {
            case "Teacher" -> {
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
                model.addAttribute("success", "You created new User have id: " + teacherNew.getId() + " Name: " + teacherNew.getFirstName());
                model.addAttribute("user", teacherNew);

            }
            case "User" -> {
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
                model.addAttribute("success", "You created new User have id: " + userNew.getId() + " Name: " + userNew.getFirstName());
                model.addAttribute("user", userNew);
            }
            case "Parent" -> {
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
                model.addAttribute("success", "You created new User have id: " + newParent.getId() + " Name: " + newParent.getFirstName());
                model.addAttribute("user", newParent);
            }
            case "Student" -> {
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
                model.addAttribute("success", "You created new User have id: " + newStudent.getId() + " Name: " + newStudent.getFirstName());
                model.addAttribute("user", newStudent);
            }
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
        // check position
        String position = userExist.getPosition();
        if(position.equals("Teacher") && (user instanceof Teacher)){
            Teacher teacherExist = (Teacher) userExist;
            teacherExist.setFirstName(user.getFirstName());
            teacherExist.setAddress(user.getAddress());
            teacherExist.setIdentity(user.getIdentity());
            teacherExist.setPhoneNumber(user.getPhoneNumber());
            teacherExist.setLastName(user.getLastName());
            teacherExist.setEmail(user.getEmail());
            teacherService.updateTeacher(teacherExist);
            model.addAttribute("success", "Modified User have id: "+ teacherExist.getId()+" Name: "+teacherExist.getFirstName());
            model.addAttribute("user", teacherExist);
        } else if (position.equals("Student") && (user instanceof Student)){
            Student studentExist = (Student) userExist;
            studentExist.setFirstName(user.getFirstName());
            studentExist.setAddress(user.getAddress());
            studentExist.setIdentity(user.getIdentity());
            studentExist.setPhoneNumber(user.getPhoneNumber());
            studentExist.setLastName(user.getLastName());
            studentExist.setEmail(user.getEmail());
            studentService.updateStudent(studentExist);
            model.addAttribute("success", "Modified User have id: "+ studentExist.getId()+" Name: "+studentExist.getFirstName());
            model.addAttribute("user", studentExist);
        } else if (position.equals("Parent") && (user instanceof Parent)){
            Parent parentExist = (Parent) userExist;
            parentExist.setFirstName(user.getFirstName());
            parentExist.setAddress(user.getAddress());
            parentExist.setIdentity(user.getIdentity());
            parentExist.setPhoneNumber(user.getPhoneNumber());
            parentExist.setLastName(user.getLastName());
            parentExist.setEmail(user.getEmail());
            parentService.updateParent(parentExist);
            model.addAttribute("success", "Modified User have id: "+ parentExist.getId()+" Name: "+parentExist.getFirstName());
            model.addAttribute("user", parentExist);
        } else {
            userExist.setFirstName(user.getFirstName());
            userExist.setAddress(user.getAddress());
            userExist.setIdentity(user.getIdentity());
            userExist.setPhoneNumber(user.getPhoneNumber());
            userExist.setLastName(user.getLastName());
            userExist.setEmail(user.getEmail());
            userService.updateUser(userExist);
            model.addAttribute("success", "Modified User have id: "+ userExist.getId()+" Name: "+userExist.getFirstName());
            model.addAttribute("user", userExist);
        }

        return "User/modifyFormUser";
    }

    @GetMapping("/modify-delete")
    public String processDelete(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        // check user
        User userExist = userService.getUserById(user.getId());
        if(userExist == null){
            redirectAttributes.addFlashAttribute("Error", "Not found User");
            return "redirect:/m-user/showManageUser";
        } else {
            // check position and remove the constraints
            switch (userExist.getPosition()) {
                case "Teacher" -> {
                    Teacher teacherExist = teacherService.getTeacherByUserNameAndId(userExist.getUsername(), userExist.getId());
                    teacherExist.setClasses(null);
                    teacherExist.setSubject(null);
                    teacherExist.setSchool(null);
                    teacherExist.getNoteBookDetailList().clear();
                    teacherExist.getStudentList().clear();
                    teacherExist.getCollectionAuthority().clear(); // xóa liên kết trong bảng bằng thủ công trước
                    // save then delete
                    teacherService.updateTeacher(teacherExist);
                    // delete
                    teacherService.deleteTeacherById(teacherExist.getId());
                    redirectAttributes.addFlashAttribute("success", "Deleted teacher has id: " + teacherExist.getId());
                }
                case "Student" -> {
                    Student studentExist = studentService.getStudentByUsernameAndId(userExist.getUsername(), userExist.getId());
                    studentExist.setParent(null);
                    studentExist.setTeacher(null);
                    studentExist.setClasses(null);
                    studentExist.setSchool(null);
                    studentExist.getCollectionAuthority().clear(); // xóa liên kết trong bảng bằng thủ công trước
                    studentExist.getScoreCardList().clear();
                    studentExist.getStudyRecordList().clear();
                    studentExist.getSubjectList().clear();
                    studentExist.getTranscriptSet().clear();
                    // save then delete
                    studentService.updateStudent(studentExist);
                    // delete
                    studentService.deleteStudentById(studentExist.getId());
                    redirectAttributes.addFlashAttribute("success", "Deleted student has id: " + studentExist.getId());
                }
                case "Parent" -> {
                    Parent parentExist = parentService.getParentByUserNameAndId(userExist.getUsername(), userExist.getId());
                    parentExist.setStudent(null);
                    parentExist.setSchool(null);
                    parentExist.getCollectionAuthority().clear(); // xóa liên kết trong bảng bằng thủ công trước
                    // save then delete
                    parentService.updateParent(parentExist);
                    // delete
                    parentService.deleteParentById(parentExist.getId());
                    redirectAttributes.addFlashAttribute("success", "Deleted parent has id: " + parentExist.getId());
                }
                default -> {
                    userExist.getCollectionAuthority().clear(); // xóa liên kết trong bảng bằng thủ công trước
                    // save then delete
                    userService.updateUser(userExist);
                    // delete
                    userService.deleteUserById(userExist.getId());
                    redirectAttributes.addFlashAttribute("success", "Deleted user has id: " + userExist.getId());
                }
            }
            return "redirect:/m-user/showManageUser";
        }
    }

    // find position in User and set Role
    public void updateRole(){
        List<User> userList = userService.getAllUser();
        for(User user : userList){
            // check position
            String position = user.getPosition();
            switch (position) {
                case "Teacher" -> {
                    Teacher teacher = new Teacher();
                    teacher.setUsername(user.getUsername());
                    teacher.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    teacher.setEnabled(true);
                    teacher.setAddress(user.getAddress());
                    teacher.setPhoneNumber(user.getPhoneNumber());
                    teacher.setIdentity(user.getIdentity());
                    teacher.setLastName(user.getLastName());
                    teacher.setFirstName(user.getFirstName());
                    teacher.setEmail(user.getEmail());
                    teacher.setPosition(user.getPosition());
                    Collection<Authority> authorityCollection = new ArrayList<>();
                    Authority defaultRole = authorityRepository.findByName("ROLE_TEACHER");
                    authorityCollection.add(defaultRole);
                    teacher.setCollectionAuthority(authorityCollection);
                    teacherService.updateTeacher(teacher);
                }
                case "Student" -> {
                    Student student = new Student();
                    student.setUsername(user.getUsername());
                    student.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    student.setEnabled(true);
                    student.setAddress(user.getAddress());
                    student.setIdentity(user.getIdentity());
                    student.setLastName(user.getLastName());
                    student.setPhoneNumber(user.getPhoneNumber());
                    student.setFirstName(user.getFirstName());
                    student.setEmail(user.getEmail());
                    student.setPosition(user.getPosition());
                    Collection<Authority> authorityCollection = new ArrayList<>();
                    Authority defaultRole = authorityRepository.findByName("ROLE_STUDENT");
                    authorityCollection.add(defaultRole);
                    student.setCollectionAuthority(authorityCollection);
                    studentService.updateStudent(student);
                }
                case "Parent" -> {
                    Parent parent = new Parent();
                    parent.setUsername(user.getUsername());
                    parent.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    parent.setEnabled(true);
                    parent.setAddress(user.getAddress());
                    parent.setIdentity(user.getIdentity());
                    parent.setLastName(user.getLastName());
                    parent.setPhoneNumber(user.getPhoneNumber());
                    parent.setFirstName(user.getFirstName());
                    parent.setEmail(user.getEmail());
                    parent.setPosition(user.getPosition());
                    Collection<Authority> authorityCollection = new ArrayList<>();
                    Authority defaultRole = authorityRepository.findByName("ROLE_PARENT");
                    authorityCollection.add(defaultRole);
                    parent.setCollectionAuthority(authorityCollection);
                    parentService.updateParent(parent);
                }
                case "User" -> {
                    User userNew = new User();
                    userNew.setUsername(user.getUsername());
                    userNew.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    userNew.setEnabled(true);
                    userNew.setAddress(user.getAddress());
                    userNew.setIdentity(user.getIdentity());
                    userNew.setLastName(user.getLastName());
                    userNew.setPhoneNumber(user.getPhoneNumber());
                    userNew.setFirstName(user.getFirstName());
                    userNew.setEmail(user.getEmail());
                    userNew.setPosition(user.getPosition());
                    Collection<Authority> authorityCollection = new ArrayList<>();
                    Authority defaultRole = authorityRepository.findByName("ROLE_USER");
                    authorityCollection.add(defaultRole);
                    userNew.setCollectionAuthority(authorityCollection);
                    userService.updateUser(userNew);
                }
            }
        }
    }

}