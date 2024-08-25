package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Integer> {

    Page<Classes> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM class WHERE class.class_name =:name AND class.c_school_id = :id", nativeQuery = true)
    Classes findClassByNameAndSchoolId(@Param("name") String name, @Param("id") int id);

    @Query(value = "SELECT * FROM class WHERE class.c_school_id=:school_id", nativeQuery = true)
    List<Classes> getListClassByIdSchool(@Param("school_id") int id_school);

    @Query(value = "SELECT * FROM class AS c JOIN student AS s ON c.c_id = s.class_id WHERE s.id=:stu_id AND c.c_school_id=:school_id", nativeQuery = true)
    Classes getClassByStudentAndSchool(@Param("stu_id") int student_id, @Param("school_id") int school_id);
}
