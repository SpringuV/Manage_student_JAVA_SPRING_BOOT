package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.SubjectDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Subject;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectDTO toDTO(Subject subject);

    Subject toEntity(SubjectDTO subjectDTO);
}
