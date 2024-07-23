package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.ClassRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

import java.util.List;

@Service
public class ClassServiceImple implements ClassService{

    private ClassRepository classRepository;

    @Autowired
    public ClassServiceImple(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<Classes> getAllClasses() {
        return this.classRepository.findAll();
    }

    @Override
    public Classes getClassById(int id) {
        return this.classRepository.findById(id).get();
    }

    @Override
    public Classes addClass(Classes classes) {
        return this.classRepository.save(classes);
    }

    @Override
    public void deleteClassById(int id) {
        this.classRepository.deleteById(id);
    }

    @Override
    public Classes updateClass(Classes classes) {
        return this.classRepository.saveAndFlush(classes);
    }
}
