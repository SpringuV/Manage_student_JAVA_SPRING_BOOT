package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Integer> {

    @Query(value = "SELECT * FROM class WHERE class.class_name =:name AND class.school_id = :id", nativeQuery = true)
    public Classes findClassByNameAndSchoolId(@Param("name") String name, @Param("id") int id);

}
