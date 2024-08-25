package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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

    @Query(value = "SELECT u.id, t.school_id, u.address, u.phone_number, u.username, u.position, u.email, u.first_name, u.last_name,u.avatar,  u.enabled, u.password, u.identity, t.subject_id, t.class_id " +
            "FROM users as u " +
            "JOIN teacher as t " +
            "ON t.id = u.id " +
            "WHERE u.position LIKE 'Teacher'", nativeQuery = true)
    List<Teacher> getAllTeacher();

    @Query(value = "SELECT * FROM teacher WHERE teacher.school_id=:school_id", nativeQuery = true)
    List<Teacher> getListTeacherByIdSchool(@Param("school_id") int school_id);

    @Query(value = "SELECT t.class_id,t.school_id,u.first_name, u.last_name, t.id, u.address, u.avatar, u.email, u.enabled, u.identity, u.password, u.phone_number, u.position, u.username, t.subject_id FROM teacher AS t RIGHT JOIN users AS u ON t.id = u.id WHERE t.school_id=:school_id AND t.class_id=:class_id", nativeQuery = true)
    List<Teacher> getListTeacherBySchoolIdAndClassID(@Param("school_id") int school_id, @Param("class_id") int class_id);

    @Modifying
    @Query("UPDATE Teacher t SET t.subject = null WHERE t.subject.id = :subjectId")
    void updateTeachersBySubjectId(@Param("subjectId") int subjectId);
}
