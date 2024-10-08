package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vutran.my_first_project_spring_boot.management_student.DTO.ScorecardDTO;
import vutran.my_first_project_spring_boot.management_student.Dao.ScorecardRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

import java.util.List;

@Service
@Transactional
public class ScorecardServiceImple implements ScoreCardService{

    private ScorecardRepository scorecardRepository;

    @Autowired
    public ScorecardServiceImple(ScorecardRepository scorecardRepository) {
        this.scorecardRepository = scorecardRepository;
    }

    @Override
    public List<ScoreCard> getAllScoreCard() {
        return this.scorecardRepository.findAll();
    }

    @Override
    public ScoreCard getScoreCardById(int id) {
        return this.scorecardRepository.findById(id).get();
    }

    @Override
    public ScoreCard addScoreCard(ScoreCard scoreCard) {
        return this.scorecardRepository.save(scoreCard);
    }

    @Override
    public void deleteScoreCardById(int id) {
        this.scorecardRepository.deleteById(id);
    }

    @Override
    public ScoreCard updateScoreCard(ScoreCard scoreCard) {
        return this.scorecardRepository.saveAndFlush(scoreCard);
    }

    @Override
    public ScoreCard getScoreCardBySchoolYear_NameExam_Student_Subject(String schoolYear, String nameExam, int student_id, int subject_id) {
        return this.scorecardRepository.getScoreCardBySchoolYear_NameExam_Student_Subject(schoolYear, nameExam, student_id, subject_id);
    }

    @Override
    public List<ScoreCard> getScorecardByStudentAndClassAndSemester(int student_id, int class_id, int semester) {
        return this.scorecardRepository.getScorecardByStudentAndClassAndSemester(student_id, class_id, semester);
    }

    @Override
    public List<ScorecardDTO> getScorecardDTOByStudentAndClassAndSemester(int student_id, int class_id, int semester) {
        return scorecardRepository.getScorecardDTOByStudentAndClassAndSemester(student_id, class_id, semester);
    }
}
