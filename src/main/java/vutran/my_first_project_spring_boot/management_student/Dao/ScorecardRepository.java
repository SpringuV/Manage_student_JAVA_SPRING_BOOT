package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

@Repository
public interface ScorecardRepository extends JpaRepository<ScoreCard, Integer> {

    Page<ScoreCard> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM score_card AS sc WHERE sc.cs_school_year LIKE :school_year AND sc.cs_name_exam LIKE :name_exam AND sc.student_id=:stu_id AND sc.cs_subject_id=:sub_id", nativeQuery = true)
    ScoreCard getScoreCardBySchoolYear_NameExam_Student_Subject(@Param("school_year") String schoolYear, @Param("name_exam") String nameExam, @Param("stu_id") int student_id, @Param("sub_id") int subject_id);
}
