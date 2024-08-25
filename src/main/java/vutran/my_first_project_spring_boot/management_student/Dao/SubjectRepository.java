package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Page<Subject> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM subject as s1 JOIN school_subject as s2 ON s1.id = s2.subject_id WHERE s2.school_id =:idSchool AND s1.subject_name =:nameSubject", nativeQuery = true)
    public Subject getSubjectBySchoolIdAndName(@Param("idSchool") int idSchool, @Param("nameSubject") String nameSubject);

    @Query(value = "SELECT * FROM subject as S JOIN school_subject as SS ON SS.subject_id = S.id WHERE SS.school_id=:schoolId", nativeQuery = true)
    public List<Subject> getListSubjectOfSchoolId(@Param("schoolId") int schoolId);

    @Query("SELECT s FROM Subject s WHERE s.nameSubject=:name")
    Subject getSubjectByName(@Param("name") String name);

    @Query("SELECT s FROM Subject s WHERE s.sub_level=:school_level")
    List<Subject> getListSubjectBySchoolLevel(@Param("school_level") String schoolLevel);
}
