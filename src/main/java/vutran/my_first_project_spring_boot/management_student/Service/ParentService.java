package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

import java.util.List;

public interface ParentService {

    Parent getParentById(int id);
    Parent addParent(Parent parent);
    void deleteParentById(int id);
    Parent updateParent(Parent parent);
    List<Parent> findALlParentByPosition();
    Parent getParentByUserNameAndId(String username, int id);
    Parent getParentByIdentity(String identity);
    Parent getParentByUserName(String username);
    Page<Parent> findParentsByFirstName(String searchName, PageRequest pageRequest);
}
