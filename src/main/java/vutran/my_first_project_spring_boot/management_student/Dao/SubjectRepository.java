package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Page<Subject> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM subjects as s1 JOIN school_subject as s2 ON s1.id = s2.subject_id WHERE s2.school_id =:idSchool AND s1.subject_name =:nameSubject", nativeQuery = true)
    Subject getSubjectBySchoolIdAndName(@Param("idSchool") int idSchool, @Param("nameSubject") String nameSubject);

    @Query(value = "SELECT * FROM subjects as S JOIN school_subject as SS ON SS.subject_id = S.id WHERE SS.school_id=:schoolId", nativeQuery = true)
    List<Subject> getListSubjectOfSchoolId(@Param("schoolId") int schoolId);

    @Query("SELECT s FROM Subject s WHERE s.nameSubject=:name")
    Subject getSubjectByName(@Param("name") String name);

    @Query("SELECT s FROM Subject s WHERE s.sub_level=:school_level")
    List<Subject> getListSubjectBySchoolLevel(@Param("school_level") String schoolLevel);

    @Query("SELECT s FROM Subject s WHERE s.nameSubject LIKE %:class_grade%")
    List<Subject> getSubjectByClassGrade(@Param("class_grade") String class_grade);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO(s.id, s.nameSubject, s.sub_level) FROM Subject s WHERE s.nameSubject LIKE %:class_grade%")
    List<SubjectDTO> getListSubjectDTOByClassGrade(@Param("class_grade") String class_grade);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO(s.id, s.nameSubject, s.sub_level) FROM Subject s " +
            "JOIN s.schoolList sc " +
            "WHERE sc.id =:schoolId")
    List<SubjectDTO> getListSubjectDTOBySchool(@Param("schoolId") int schoolId);

    @Query("SELECT new vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO(s.id, s.nameSubject, s.sub_level) FROM Subject s WHERE s.sub_level=:school_level")
    List<SubjectDTO> getListSubjectDTOBySchoolLevel(@Param("school_level") String schoolLevel);
}
