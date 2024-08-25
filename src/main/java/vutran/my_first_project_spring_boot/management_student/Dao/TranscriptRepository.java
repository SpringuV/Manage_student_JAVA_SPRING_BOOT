package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

    Page<Transcript> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM transcript as T WHERE T.transcript_semester =:semester AND T.school_year =:schoolYear", nativeQuery = true)
    Transcript getTranscriptBySemesterAndSchoolYear(@Param("semester") int semester,@Param("schoolYear") String schoolYear);
}
