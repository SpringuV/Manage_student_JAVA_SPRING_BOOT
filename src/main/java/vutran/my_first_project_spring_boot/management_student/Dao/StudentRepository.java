package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.position = 'Student'", nativeQuery = true)
    List<Student> findListStudentByPosition();

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.username =:username AND u.position LIKE 'Student' AND u.id =:user_id",nativeQuery = true)
    Student getStudentByUserNameAndId(@Param("username") String username, @Param("user_id") int id);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.username =:username AND u.position LIKE 'Student'",nativeQuery = true)
    Student getStudentByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.identity =:identity",nativeQuery = true)
    Student getStudentByIdentity(@Param("identity") String identity);

    @Query(value = "SELECT * FROM student AS s JOIN users AS u ON s.id = u.id WHERE s.school_id =:school_id", nativeQuery = true)
    List<Student> getListStudentBySchoolId(@Param("school_id") int school_id);

    @Query(value = "SELECT * FROM users AS u JOIN student AS s ON s.id = u.id WHERE s.school_id=:school_id AND s.class_id=:class_id", nativeQuery = true)
    List<Student> getListStudentByClassAndSchool(@Param("class_id") int class_id, @Param("school_id") int school_id);
}
