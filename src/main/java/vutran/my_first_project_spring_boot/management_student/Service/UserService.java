package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUser();
    public User getUserById(int id);
    public User addUser(User user);
    public void deleteUserById(int id);
    public User updateUser(User user);
    public User fineUserByName(String username);
    public List<User> getListUserByPosition(String position);
}
