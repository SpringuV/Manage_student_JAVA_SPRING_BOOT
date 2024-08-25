package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBook;

@Repository
public interface NotebookRepository extends JpaRepository<NoteBook, Integer> {

    Page<NoteBook> findAll (Pageable pageable);

    // filter data matches
    @Query(value = "SELECT * FROM note_book WHERE note_class_id =:class_id AND school_id =:s_id ",nativeQuery = true)
    public NoteBook findNoteBookByClassIdAndSchoolId(@Param("class_id") int classes_id,@Param("s_id") int school_id);
}
