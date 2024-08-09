package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;

@Repository
public interface StudyrecordRepository extends JpaRepository<StudyRecord, Integer> {

    @Query(value = "SELECT * FROM study_record AS sr WHERE sr.sr_student_id=:student_id AND sr.school_id=:school_id AND sr.school_year LIKE :schoolYear", nativeQuery = true)
    StudyRecord getStudyRecordByStudentAndSchoolAndSchoolYear(@Param("student_id") int student_id,@Param("school_id") int school_id,@Param("schoolYear") String schoolYear);
}
