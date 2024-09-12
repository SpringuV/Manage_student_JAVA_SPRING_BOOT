package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.DTO.ScorecardDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

import java.util.List;

public interface ScoreCardService {
     List<ScoreCard> getAllScoreCard();
     ScoreCard getScoreCardById(int id);
     ScoreCard addScoreCard(ScoreCard scoreCard);
     void deleteScoreCardById(int id);
     ScoreCard updateScoreCard(ScoreCard scoreCard);
     ScoreCard getScoreCardBySchoolYear_NameExam_Student_Subject(String schoolYear, String nameExam, int student_id, int subject_id);
     List<ScoreCard> getScorecardByStudentAndClassAndSemester(int student_id, int class_id, int semester);
     List<ScorecardDTO> getScorecardDTOByStudentAndClassAndSemester(int student_id, int class_id, int semester);
}
