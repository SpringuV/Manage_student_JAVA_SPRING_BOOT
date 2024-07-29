package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username LIKE :username")
    public User findUserByUserName (String username);

    @Query("SELECT u FROM User u WHERE u.identity LIKE :identity")
    public User findUserByIdentity(String identity);

    @Query("SELECT u FROM User u WHERE u.position LIKE :position")
    public List<User> findAllUserByPosition(String position);
}
