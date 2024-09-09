package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.ClassDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Classes;

@Mapper
public interface ClassMapper {

    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    ClassDTO toDTO(Classes classes);

    Classes toEntity(ClassDTO classDisplayDTO);
}
