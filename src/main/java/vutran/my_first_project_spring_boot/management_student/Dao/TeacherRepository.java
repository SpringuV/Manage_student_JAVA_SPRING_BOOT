package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

//     native query
    @Query(value = "SELECT * FROM users JOIN teacher ON teacher.id = user.id WHERE users.first_name = :firstname", nativeQuery = true)
    public List<Teacher> findTeacherByName(@Param("firstname") String firstName);

    @Query(value = "SELECT u.id, t.school_id, u.address, u.phone_number, u.username, u.position, u.email, u.first_name, u.last_name,u.avatar,  u.enabled, u.password " +
            "FROM users as u " +
            "JOIN teacher as t " +
            "ON t.id = u.id " +
            "WHERE u.position LIKE 'Teacher'", nativeQuery = true)
    public List<Teacher> getAllTeacher();
}
