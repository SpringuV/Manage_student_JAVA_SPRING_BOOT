package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;

import java.util.Date;
import java.util.List;

@Repository
public interface DetailNoteRepository extends JpaRepository<NoteBookDetail, Integer> {

    @Query(value = "SELECT * FROM notebook_detail JOIN note_book ON note_book.note_id = notebook_detail.notebook_id WHERE notebook_detail.notebook_id =:id",nativeQuery = true)
    public List<NoteBookDetail> getDetailNoteByNoteBookId(@Param("id") int id);

    // check for duplicates
    @Query(value = "SELECT * FROM notebook_detail as nd WHERE nd.note_time =:time AND nd.subject_id =:sub_id AND nd.note_teachday =:day_teach", nativeQuery = true)
    public List<NoteBookDetail> getNoteBookDetailDuplicates(@Param("time") String time, @Param("sub_id") int sub_id, @Param("day_teach") Date dayTeach);

}
