package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.StudentDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.Student;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);
}
