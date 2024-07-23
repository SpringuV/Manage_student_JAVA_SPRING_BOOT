package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    public Authority findByName(String username);
}
