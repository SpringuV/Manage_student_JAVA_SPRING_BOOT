package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.DTO.TranscriptDTO;
import vutran.my_first_project_spring_boot.management_student.Dao.TranscriptRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

import java.util.List;

@Service
@Transactional
public class TranscriptServiceImple implements TranscriptService{

    private TranscriptRepository transcriptRepository;

    @Autowired
    public TranscriptServiceImple(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    @Override
    public List<Transcript> getAllTranscripts() {
        return this.transcriptRepository.findAll();
    }

    @Override
    public Transcript getTranscriptById(int id) {
        return this.transcriptRepository.findById(id).get();
    }

    @Override
    public Transcript addTranscript(Transcript transcript) {
        return this.transcriptRepository.save(transcript);
    }

    @Override
    public void deleteTranscriptById(int id) {
        this.transcriptRepository.deleteById(id);
    }

    @Override
    public Transcript updateTranscript(Transcript transcript) {
        return this.transcriptRepository.saveAndFlush(transcript);
    }

    @Override
    public Transcript getTranscriptBySemesterAndSchoolYearAndName(int semester, String schoolYear, int school_id, String nameTranscript) {
        return this.transcriptRepository.getTranscriptBySemesterAndSchoolYearAndName(semester, schoolYear, school_id, nameTranscript);
    }

    @Override
    public List<Transcript> getTranscriptBySchool(int school_id) {
        return transcriptRepository.getTranscriptBySchool(school_id);
    }

    @Override
    public Page<Transcript> getTranscriptBySchoolName(String school_name, PageRequest pageRequest) {
        return transcriptRepository.getTranscriptBySchoolName(school_name, pageRequest);
    }

    @Override
    public List<TranscriptDTO> getListTranscriptDTOBySchool(int school_id) {
        return transcriptRepository.getListTranscriptDTOBySchool(school_id);
    }
}
