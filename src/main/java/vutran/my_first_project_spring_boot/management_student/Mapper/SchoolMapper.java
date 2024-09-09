package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.SchoolDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.School;

@Mapper
public interface SchoolMapper {

    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    SchoolDTO toDTO(School school);

    School toEntity(SchoolDTO schoolDTO);
}
