package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

import java.util.List;

public interface TranscriptService {
    public List<Transcript> getAllTranscripts();
    public Transcript getTranscriptById(int id);
    public Transcript addTranscript(Transcript transcript);
    public void deleteTranscriptById(int id);
    public Transcript updateTranscript(Transcript transcript);
}
