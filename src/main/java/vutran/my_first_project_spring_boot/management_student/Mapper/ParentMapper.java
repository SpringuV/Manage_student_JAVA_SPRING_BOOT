package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.ParentDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Parent;

@Mapper
public interface ParentMapper {

    ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);

    ParentDTO toDTO(Parent parent);

    Parent toEntity(ParentDTO parentDTO);

}
