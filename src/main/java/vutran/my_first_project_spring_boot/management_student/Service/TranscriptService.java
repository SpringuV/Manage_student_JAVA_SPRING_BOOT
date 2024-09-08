package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

import java.util.List;

public interface TranscriptService {
    List<Transcript> getAllTranscripts();
    Transcript getTranscriptById(int id);
    Transcript addTranscript(Transcript transcript);
    void deleteTranscriptById(int id);
    Transcript updateTranscript(Transcript transcript);
    Transcript getTranscriptBySemesterAndSchoolYearAndName(int semester, String schoolYear, int school_id, String nameTranscript);
    List<Transcript> getTranscriptBySchool(int school_id);
    Page<Transcript> getTranscriptBySchoolName(String school_name, PageRequest pageRequest);
}
