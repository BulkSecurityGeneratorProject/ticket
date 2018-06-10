package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, TasksMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "tasks.id", target = "tasksId")
    FileDTO toDto(File file);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "tasksId", target = "tasks")
    File toEntity(FileDTO fileDTO);

    default File fromId(Long id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
