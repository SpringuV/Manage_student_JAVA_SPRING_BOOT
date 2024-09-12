package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Page<Teacher> findAll(Pageable pageable);

//     native query
    @Query(value = "SELECT * FROM users JOIN teacher ON teacher.id = users.id WHERE users.username = :username AND users.position LIKE 'Teacher' AND users.id=:user_id", nativeQuery = true)
    Teacher findTeacherByUserNameAndId(@Param("username") String username, @Param("user_id") int id);

    @Query(value = "SELECT * FROM users JOIN teacher ON teacher.id = users.id WHERE users.username = :username AND users.position LIKE 'Teacher'", nativeQuery = true)
    Teacher findTeacherByUserName(@Param("username") String username);

    @Query("SELECT t FROM Teacher t")
    List<Teacher> getAllTeacher();

    @Query(value = "SELECT * FROM teacher WHERE teacher.school_id=:school_id", nativeQuery = true)
    List<Teacher> getListTeacherByIdSchool(@Param("school_id") int school_id);

    @Query(value = "SELECT t.class_id,t.school_id,u.first_name, u.last_name, t.id, u.address, u.avatar, u.email, u.enabled, u.identity, u.password, u.phone_number, u.position, u.username, t.subject_id FROM teacher AS t RIGHT JOIN users AS u ON t.id = u.id WHERE t.school_id=:school_id AND t.class_id=:class_id", nativeQuery = true)
    List<Teacher> getListTeacherBySchoolIdAndClassID(@Param("school_id") int school_id, @Param("class_id") int class_id);

    @Modifying
    @Query("UPDATE Teacher t SET t.subject = null WHERE t.subject.id = :subjectId")
    void updateTeachersBySubjectId(@Param("subjectId") int subjectId);

    @Query("SELECT t FROM Teacher t WHERE t.firstName LIKE %:searchName%")
    Page<Teacher> findTeachersByFirstName(@Param("searchName") String searchName, PageRequest pageRequest);

    @Query("SELECT t FROM Teacher t WHERE t.classes.id=:id_class")
    List<Teacher> getListTeacherByClass(@Param("id_class") int class_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO(t.id, t.firstName, t.lastName) FROM Teacher t WHERE t.classes.id=:id_class")
    List<TeacherDTO> getListTeacherDTOByClass(@Param("id_class") int class_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO(t.id, t.firstName, t.lastName) FROM Teacher t WHERE t.school.id=:school_id AND t.classes.id=:class_id")
    List<TeacherDTO> getListTeacherDTOBySchoolIdAndClassID(@Param("school_id") int school_id, @Param("class_id") int class_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO(t.id, t.firstName, t.lastName) FROM Teacher t")
    List<TeacherDTO> getAllTeacherDTO();

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO(t.id, t.firstName, t.lastName) FROM Teacher t WHERE t.school.id=:school_id")
    List<TeacherDTO> getListTeacherDTOByIdSchool(@Param("school_id") int school_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO(t.id, t.firstName, t.lastName) FROM Teacher t WHERE t.school.id=:school_id AND t.classes.id=:class_id AND t.subject.id=:subject_id")
    List<TeacherDTO> getListTeacherDTOBySchoolAndClassAndSubject(@Param("school_id") int school_id, @Param("class_id") int class_id, @Param("subject_id") int subject_id);
}
