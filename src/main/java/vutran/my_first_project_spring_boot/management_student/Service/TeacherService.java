package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface TeacherService {
    List<Teacher> getAllTeacher();
    Teacher getTeacherById(int id);
    Teacher addTeacher(Teacher teacher);
    void deleteTeacherById(int id);
    Teacher updateTeacher(Teacher teacher);
    Teacher getTeacherByUserNameAndId(String username, int user_id);
    List<Teacher> getListTeacherByPosition();
    Optional<Teacher> findById(int id);
    List<Teacher> getListTeacherByIdSchool(int school_id);
    List<Teacher> getListTeacherBySchoolIdAndClassID(int school_id, int class_id);
    Teacher getTeacherByUserName(String username);
    void updateTeachersBySubjectId(int subject_id);
    Page<Teacher> findTeachersByFirstName(String searchName, PageRequest pageRequest);
    List<Teacher> getListTeacherByClass(int id_class);
    List<TeacherDTO> getListTeacherDTOByClass(int class_id);
    List<TeacherDTO> getListTeacherDTOBySchoolIdAndClassID(int school_id, int class_id);
    List<TeacherDTO> getAllTeacherDTO();
    List<TeacherDTO> getListTeacherDTOByIdSchool(int school_id);
}
