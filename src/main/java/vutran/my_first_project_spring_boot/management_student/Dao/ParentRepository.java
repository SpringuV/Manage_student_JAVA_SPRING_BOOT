package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

    Page<Parent> findAll (Pageable pageable);

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.position = 'Parent'",nativeQuery = true)
    List<Parent> findParentByPosition();

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.username =:username AND u.position LIKE 'Parent' AND u.id=:user_id",nativeQuery = true)
    Parent findParentByUserNameAndId(@Param("username") String username, @Param("user_id") int id);

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.username =:username AND u.position LIKE 'Parent'",nativeQuery = true)
    Parent findParentByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.identity =:identity",nativeQuery = true)
    Parent findParentByIdentity(@Param("identity") String identity);

    @Query("SELECT p FROM Parent p WHERE p.firstName LIKE %:searchName%")
    Page<Parent> findParentsByFirstName(@Param("searchName") String searchName, PageRequest pageRequest);

    @Modifying
    @Query("UPDATE Parent p SET p.classes.id= NULL WHERE p.classes.id=:class_id")
    void updateClassIdToNullForClass(@Param("class_id") int classId);
}
