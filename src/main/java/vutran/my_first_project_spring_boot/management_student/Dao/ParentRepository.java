package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.position = 'Parent'",nativeQuery = true)
    public List<Parent> findParentByPosition();

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.username =:username",nativeQuery = true)
    public Parent findParentByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM users as u JOIN parent as p ON p.id = u.id WHERE u.identity =:identity",nativeQuery = true)
    public Parent findParentByIdentity(@Param("identity") String identity);
}
