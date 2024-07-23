package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.Parent;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

import java.util.List;

public interface ScoreCardService {
    public List<ScoreCard> getAllScoreCard();
    public ScoreCard getScoreCardById(int id);
    public ScoreCard addScoreCard(ScoreCard scoreCard);
    public void deleteScoreCardById(int id);
    public ScoreCard updateScoreCard(ScoreCard scoreCard);
}
