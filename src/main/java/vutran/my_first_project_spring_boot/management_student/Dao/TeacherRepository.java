package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

//     native query
    @Query(value = "SELECT * FROM users JOIN teacher ON teacher.id = users.id WHERE users.username = :username", nativeQuery = true)
    public Teacher findTeacherByUserName(@Param("username") String username);

    @Query(value = "SELECT u.id, t.school_id, u.address, u.phone_number, u.username, u.position, u.email, u.first_name, u.last_name,u.avatar,  u.enabled, u.password, u.identity, t.subject_id " +
            "FROM users as u " +
            "JOIN teacher as t " +
            "ON t.id = u.id " +
            "WHERE u.position LIKE 'Teacher'", nativeQuery = true)
    public Set<Teacher> getAllTeacher();
}
