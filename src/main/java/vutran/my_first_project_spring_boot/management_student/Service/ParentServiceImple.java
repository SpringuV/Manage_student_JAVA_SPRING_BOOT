package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.ParentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

import java.util.List;

@Service
public class ParentServiceImple implements ParentService{

    private ParentRepository parentRepository;

    @Autowired
    public ParentServiceImple(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Parent getParentById(int id) {
        return parentRepository.findById(id).get();
    }

    @Override
    public Parent addParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public void deleteParentById(int id) {
        parentRepository.deleteById(id);
    }

    @Override
    public Parent updateParent(Parent parent) {
        return parentRepository.saveAndFlush(parent);
    }

    @Override
    public List<Parent> findALlParentByPosition() {
        return parentRepository.findParentByPosition();
    }

    @Override
    public Parent getParentByUsername(String username) {
        return parentRepository.findParentByUserName(username);
    }

    @Override
    public Parent getParentByIdentity(String identity) {
        return parentRepository.findParentByIdentity(identity);
    }
}
