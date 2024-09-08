package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

    Page<Transcript> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM transcript as T WHERE T.transcript_semester =:semester AND T.school_year =:schoolYear AND T.school_id=:school_id AND t.name_transcript LIKE %:name_transcript%", nativeQuery = true)
    Transcript getTranscriptBySemesterAndSchoolYearAndName(@Param("semester") int semester,@Param("schoolYear") String schoolYear, @Param("school_id") int school_id, @Param("name_transcript") String nameTranscript);

    @Query("SELECT t FROM Transcript t WHERE t.school.id = :school_id")
    List<Transcript> getTranscriptBySchool(@Param("school_id") int school_id);

    @Query("SELECT t FROM Transcript t WHERE t.school.name LIKE %:school_name%")
    Page<Transcript> getTranscriptBySchoolName(@Param("school_name") String school_name, PageRequest pageRequest);
}
