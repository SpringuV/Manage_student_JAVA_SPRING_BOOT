package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

import java.util.List;

public interface ParentService {

    public Parent getParentById(int id);
    public Parent addParent(Parent parent);
    public void deleteParentById(int id);
    public Parent updateParent(Parent parent);
    public List<Parent> findALlParentByPosition();
    public Parent getParentByUserNameAndId(String username, int id);
    public Parent getParentByIdentity(String identity);
    public Parent getParentByUserName(String username);
}
