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

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.username =:username",nativeQuery = true)
    Student getStudentByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.identity =:identity",nativeQuery = true)
    Student getStudentByIdentity(@Param("identity") String identity);
}
