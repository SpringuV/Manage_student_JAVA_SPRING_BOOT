package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.DTO.StudentDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.position = 'Student'", nativeQuery = true)
    List<Student> findListStudentByPosition();

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.username =:username AND u.position LIKE 'Student' AND u.id =:user_id", nativeQuery = true)
    Student getStudentByUserNameAndId(@Param("username") String username, @Param("user_id") int id);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.username =:username AND u.position LIKE 'Student'", nativeQuery = true)
    Student getStudentByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM users as u JOIN student as s ON u.id = s.id WHERE u.identity =:identity", nativeQuery = true)
    Student getStudentByIdentity(@Param("identity") String identity);

    @Query(value = "SELECT * FROM student AS s JOIN users AS u ON s.id = u.id WHERE s.school_id =:school_id", nativeQuery = true)
    List<Student> getListStudentBySchoolId(@Param("school_id") int school_id);

    @Query(value = "SELECT * FROM users AS u JOIN student AS s ON s.id = u.id WHERE s.school_id=:school_id AND s.class_id=:class_id", nativeQuery = true)
    List<Student> getListStudentByClassAndSchool(@Param("class_id") int class_id, @Param("school_id") int school_id);

    @Query("SELECT s FROM Student s WHERE s.classes.id=:id_class")
    List<Student> getListByClassId(@Param("id_class") int class_id);

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:searchName%")
    Page<Student> findStudentsByFirstName(@Param("searchName") String searchName, PageRequest pageRequest);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.StudentDTO(s.id, s.firstName, s.lastName) FROM Student s WHERE s.school.id=:school_id")
    List<StudentDTO> getListStudentDTOBySchoolId(@Param("school_id") int school_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.StudentDTO(s.id, s.firstName, s.lastName) FROM Student s WHERE s.classes.id =:class_id AND s.school.id=:school_id")
    List<StudentDTO> getListStudentDTOByClassAndSchool(@Param("class_id") int class_id, @Param("school_id") int school_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.StudentDTO(s.id, s.firstName, s.lastName) FROM Student s WHERE s.classes.id =:id_class")
    List<StudentDTO> getListStudentDTOByClassId(@Param("id_class") int class_id);
}
