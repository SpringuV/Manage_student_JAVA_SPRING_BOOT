package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.DTO.ScorecardDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

import java.util.List;

@Repository
public interface ScorecardRepository extends JpaRepository<ScoreCard, Integer> {

    Page<ScoreCard> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM score_card AS sc WHERE sc.cs_school_year LIKE :school_year AND sc.cs_name_exam LIKE :name_exam AND sc.student_id=:stu_id AND sc.cs_subject_id=:sub_id", nativeQuery = true)
    ScoreCard getScoreCardBySchoolYear_NameExam_Student_Subject(@Param("school_year") String schoolYear, @Param("name_exam") String nameExam, @Param("stu_id") int student_id, @Param("sub_id") int subject_id);

    @Query("SELECT sc FROM ScoreCard sc WHERE sc.student.classes.id =:class_id AND sc.student.id=:student_id AND sc.semester =:semester")
    List<ScoreCard> getScorecardByStudentAndClassAndSemester(@Param("student_id") int student_id, @Param("class_id") int class_id, @Param("semester") int semester);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.ScorecardDTO(sc.id, sc.nameExam, sc.schoolYear, sc.score, sc.semester, sc.dayExam, sc.subject.id, sc.subject.nameSubject)" +
            " FROM ScoreCard sc WHERE sc.student.classes.id=:class_id AND sc.student.id=:student_id AND sc.semester=:semester")
    List<ScorecardDTO> getScorecardDTOByStudentAndClassAndSemester(@Param("student_id") int student_id, @Param("class_id") int class_id, @Param("semester") int semester);
}
