package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.TranscriptRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

import java.util.List;

@Service
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
    public Transcript getTranscriptBySemesterAndSchoolYear(int semester, String schoolYear) {
        return this.transcriptRepository.getTranscriptBySemesterAndSchoolYear(semester, schoolYear);
    }
}
