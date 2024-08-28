package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.Dao.ClassRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.StudentRepository;
import vutran.my_first_project_spring_boot.management_student.Dao.TeacherRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.List;

@Service
public class ClassServiceImple implements ClassService{

    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public ClassServiceImple(ClassRepository classRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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
        try {
            // Lấy danh sách các sinh viên, teacher liên kết với class cần xóa
            List<Student> students = studentRepository.getListByClassId(id);
            List<Teacher> teachers = teacherRepository.getListTeacherByClass(id);
            // Đặt class_id của các sinh viên này thành null
            for (Student student : students) {
                student.setClasses(null);
                studentRepository.saveAndFlush(student);
            }
            for (Teacher teacher : teachers){
                teacher.setClasses(null);
                teacherRepository.saveAndFlush(teacher);
            }
            classRepository.deleteById(id);
        } catch (Exception e){
            // Xử lý lỗi nếu có
            e.printStackTrace();
            throw new RuntimeException("Failed to delete class with id: " + id, e);
        }

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
