package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.School;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

    // jpql query to find school by name
    @Query("SELECT s FROM School s WHERE s.name LIKE %:name%")
    List<School> findByNameSchoolsPattern(@Param("name") String name);

    @Query("SELECT s FROM School s WHERE s.name LIKE :name")
    School findByNameSchool(@Param("name") String name);
}
