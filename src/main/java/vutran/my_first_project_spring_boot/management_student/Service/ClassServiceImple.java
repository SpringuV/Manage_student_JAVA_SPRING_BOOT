package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.ClassRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

@Service
public class ClassServiceImple implements ClassService{

    private ClassRepository classRepository;
    private StudentRepository studentRepository;

    @Autowired
    public ClassServiceImple(ClassRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
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

    @Transactional
    @Override
    public void deleteClassById(int id) {
        // Lấy danh sách các sinh viên liên kết với class cần xóa
        List<Student> students = studentRepository.getListByClassId(id);

        // Đặt class_id của các sinh viên này thành null
        for (Student student : students) {
            student.setClasses(null);
            studentRepository.saveAndFlush(student);
        }

        // Sau đó xóa class
        this.classRepository.deleteById(id);
    }

    @Override
    public Classes updateClass(Classes classes) {
        return this.classRepository.saveAndFlush(classes);
    }

    @Override
    public Classes findClassByNameAndSchoolId(String name, int id) {
        return this.classRepository.findClassByNameAndSchoolId(name, id);
    }

    @Override
    public List<Classes> getListClassByIdSchool(int school_id) {
        return this.classRepository.getListClassByIdSchool(school_id);
    }

    @Override
    public Classes getClassByStudentAndSchool(int student_id, int school_id) {
        return classRepository.getClassByStudentAndSchool(student_id, school_id);
    }
}
