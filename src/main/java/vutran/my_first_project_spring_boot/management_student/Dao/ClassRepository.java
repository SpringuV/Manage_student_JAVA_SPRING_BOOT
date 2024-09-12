package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.DTO.ClassDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Integer> {

    Page<Classes> findAll(Pageable pageable);

    @Query("SELECT c FROM Classes c WHERE c.name=:name AND c.school.id=:id")
    Classes findClassByNameAndSchoolId(@Param("name") String name, @Param("id") int id);

    @Query("SELECT c FROM Classes c WHERE c.school.id=:school_id")
    List<Classes> getListClassByIdSchool(@Param("school_id") int id_school);

    @Query("SELECT c FROM Classes c JOIN c.studentList st WHERE st.id=:stu_id AND c.school.id=:school_id")
    Classes getClassByStudentAndSchool(@Param("stu_id") int student_id, @Param("school_id") int school_id);

    @Query("SELECT c.grade FROM Classes c WHERE c.id = :id_class")
    String getGrade(@Param("id_class") int class_id);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.ClassDTO(c.id, c.grade, c.name) FROM Classes c WHERE c.school.id =:school_id")
    List<ClassDTO> getListClassDTOBySchool(@Param("school_id") int school_id);
}
