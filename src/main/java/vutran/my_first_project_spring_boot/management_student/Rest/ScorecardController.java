package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;
import vutran.my_first_project_spring_boot.management_student.Service.ScoreCardService;

import java.util.List;

@Controller
@RequestMapping("/api-score-card")
public class ScorecardController {
    private ScoreCardService scoreCardService;

    @Autowired
    public ScorecardController(ScoreCardService scoreCardService) {
        this.scoreCardService = scoreCardService;
    }

    // get all
    @GetMapping
    public List<ScoreCard> getAllScoreCard(){
        return scoreCardService.getAllScoreCard();
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<ScoreCard> addScoreCard(@RequestBody ScoreCard scoreCard){// tu dong bien json thanh students
        scoreCard.setId(0); //bat buoc them moi va tu phat sinh ra id khi khach hang co nhap id
        scoreCard = scoreCardService.addScoreCard(scoreCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreCard);
    }

    // modify parent
    @PutMapping("/modify/{id}")
    public ResponseEntity<ScoreCard> modifyUpdateScoreCard(@PathVariable int id, @RequestBody ScoreCard scoreCard){
        ScoreCard scorecardExist = scoreCardService.getScoreCardById(id);
        if(scorecardExist != null){
            scorecardExist.setDayExam(scoreCard.getDayExam());
            scorecardExist.setScore(scoreCard.getScore());
            scorecardExist.setStudent(scoreCard.getStudent());
            scorecardExist.setSubjectSet(scoreCard.getSubjectSet());
            scoreCardService.updateScoreCard(scorecardExist);
            return ResponseEntity.ok(scorecardExist);
        } else {
            try {
                throw new Exception("Not found ScoreCard have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ScoreCard> deleteScoreCardById(@PathVariable int id){
        ScoreCard scoreCardExist = scoreCardService.getScoreCardById(id);
        if(scoreCardExist != null){
            scoreCardService.deleteScoreCardById(id);
            return ResponseEntity.ok().build();
        } else {
            try {
                throw new Exception("Not found parent have id: "+ id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
