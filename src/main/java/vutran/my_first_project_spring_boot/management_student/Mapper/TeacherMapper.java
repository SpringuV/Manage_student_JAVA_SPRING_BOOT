package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.TeacherDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Teacher;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDTO toDTO(Teacher teacher);

    Teacher toEntity(TeacherDTO teacherDTO);

}
