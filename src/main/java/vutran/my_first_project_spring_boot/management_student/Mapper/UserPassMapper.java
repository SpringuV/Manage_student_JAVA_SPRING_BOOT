package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.UserPassDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

@Mapper
public interface UserPassMapper {
    UserPassMapper INSTANCE = Mappers.getMapper(UserPassMapper.class);

    UserPassDTO toDTO(User user);

    User toEntity(UserPassDTO userPassDTO);
}
