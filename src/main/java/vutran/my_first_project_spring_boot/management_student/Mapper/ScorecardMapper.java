package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.ScorecardDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.ScoreCard;

@Mapper
public interface ScorecardMapper {
    ScorecardMapper INSTANCE = Mappers.getMapper(ScorecardMapper.class);

    ScoreCard toEntity(ScorecardDTO scorecardDTO);

    ScorecardDTO toDTO(ScoreCard scoreCard);
}
