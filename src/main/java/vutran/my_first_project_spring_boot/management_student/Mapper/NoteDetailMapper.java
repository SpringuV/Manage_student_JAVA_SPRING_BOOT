package vutran.my_first_project_spring_boot.management_student.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vutran.my_first_project_spring_boot.management_student.DTO.NotebookDetailDTO;
import vutran.my_first_project_spring_boot.management_student.Entity.NoteBookDetail;

@Mapper
public interface NoteDetailMapper {

    NoteDetailMapper INSTANCE = Mappers.getMapper(NoteDetailMapper.class);

    NotebookDetailDTO toDto(NoteBookDetail noteBookDetail);

    NoteBookDetail toEntity(NotebookDetailDTO notebookDetailDTO);

}
