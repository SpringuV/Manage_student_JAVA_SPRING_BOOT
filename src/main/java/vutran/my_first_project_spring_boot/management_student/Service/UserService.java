package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import vutran.my_first_project_spring_boot.management_student.DTO.UserPassDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUser();

    User getUserById(int id);

    User addUser(User user);

    void deleteUserById(int id);

    User updateUser(User user);

    User findUserByIdentity(String position);

    User findUserByName(String username);

    List<User> getListUserByPosition(String position);

    Page<User> getListUserByFirstName(String firstName, PageRequest pageRequest);

    UserPassDTO getUserPassDTOById(int id);
}
