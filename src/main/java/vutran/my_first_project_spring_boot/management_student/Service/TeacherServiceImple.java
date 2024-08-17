package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.TeacherRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return teacherRepository.getAllTeacher();
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Transactional
    @Override
    public void deleteTeacherById(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher getTeacherByUserNameAndId(String username, int user_id) {
        return teacherRepository.findTeacherByUserNameAndId(username, user_id);
    }

    @Override
    public List<Teacher> getListTeacherByPosition() {
        return teacherRepository.getAllTeacher();
    }

    @Override
    public Optional<Teacher> findById(int id) {
        return teacherRepository.findById(id);
    }

    @Override
    public List<Teacher> getListTeacherByIdSchool(int school_id) {
        return teacherRepository.getListTeacherByIdSchool(school_id);
    }

    @Override
    public List<Teacher> getListTeacherBySchoolIdAndClassID(int school_id, int class_id) {
        return teacherRepository.getListTeacherBySchoolIdAndClassID(school_id, class_id);
    }

    @Override
    public Teacher getTeacherByUserName(String username) {
        return teacherRepository.findTeacherByUserName(username);
    }
}
