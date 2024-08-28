package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username LIKE :username")
    User findUserByUserName (String username);

    @Query("SELECT u FROM User u WHERE u.identity LIKE :identity")
    User findUserByIdentity(String identity);

    @Query("SELECT u FROM User u WHERE u.position LIKE :position")
    List<User> findAllUserByPosition(String position);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:searchName%")
    Page<User> findUsersByFirstName(@Param("searchName") String searchName, PageRequest pageRequest);
}
