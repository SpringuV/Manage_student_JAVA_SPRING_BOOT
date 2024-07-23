package vutran.my_first_project_spring_boot.management_student.Rest;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api-user")
public class UserController {
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

//    @PostConstruct
//    public void insertUser(){
//        User user1 = new User();
//        user1.setUsername("vutran");
//        user1.setPassword("$2a$12$ERJ6wlxdoPx5bcUPTrslOelMYR/wKxAqj8YdUrj855rRsiJZlHpYC");
//        user1.setEnabled(true);
//
//        Authority role1 = new Authority();
//        role1.setName("ROLE_USER");
//
//        Collection<Authority> roles = new ArrayList<>();
//        roles.add(role1);
//
//        user1.setCollectionAuthority(roles);
//
//        userService.addUser(user1);
//    }

    // get all
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){// tu dong bien json thanh user
        user.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        user = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable int id, @RequestBody User user){
        User userExist = userService.getUserById(id);
        if(userExist != null){
            userExist.setAddress(user.getAddress());
            userExist.setCollectionAuthority(user.getCollectionAuthority());
            userExist.setEnabled(user.getEnabled());
            userExist.setPassword(user.getPassword());
            userExist.setFirstName(user.getFirstName());
            userExist.setLastName(user.getLastName());
            userExist.setPhoneNumber(user.getPhoneNumber());
            userService.updateUser(userExist);
            return ResponseEntity.ok(userExist);
        } else {
            try {
                throw new Exception("Not found ScoreCard have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable int id){
        User userExist = userService.getUserById(id);
        if(userExist != null){
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } else {
            try {
                throw new Exception("Not found parent have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
