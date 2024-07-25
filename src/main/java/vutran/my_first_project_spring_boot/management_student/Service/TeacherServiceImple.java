package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.TeacherRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.Collections;
import java.util.List;

@Service
public class TeacherServiceImple implements TeacherService{

    private TeacherRepository teacherRepository;
    private UserRepository userRepository;

    @Autowired
    public TeacherServiceImple(TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teacherRepository.findById(id).get();
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public List<Teacher> fineTeacherByName(String username) {
        return teacherRepository.findTeacherByName(username);
    }

    @Override
    public List<Teacher> getListTeacherByPosition() {
        return teacherRepository.getAllTeacher();
    }
}
