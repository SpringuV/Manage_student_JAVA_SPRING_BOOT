package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

import java.util.List;

public interface ScoreCardService {
    public List<ScoreCard> getAllScoreCard();
    public ScoreCard getScoreCardById(int id);
    public ScoreCard addScoreCard(ScoreCard scoreCard);
    public void deleteScoreCardById(int id);
    public ScoreCard updateScoreCard(ScoreCard scoreCard);
    public ScoreCard getScoreCardBySchoolYear_NameExam_Student_Subject(String schoolYear, String nameExam, int student_id, int subject_id);
}
