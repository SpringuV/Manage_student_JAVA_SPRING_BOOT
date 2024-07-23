package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.StudentCard;

@Repository
public interface StudentcardRepository extends JpaRepository<StudentCard, Integer> {
}
