package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.TranscriptDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Transcript;

@Mapper
public interface TranscriptMapper {

    TranscriptMapper INSTANCE = Mappers.getMapper(TranscriptMapper.class);

    TranscriptDTO toDTO(Transcript transcript);

    Transcript toEntity(TranscriptDTO transcriptDTO);

}
