package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.TasksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tasks and its DTO TasksDTO.
 */
@Mapper(componentModel = "spring", uses = {AssigsMapper.class, ProjectMapper.class})
public interface TasksMapper extends EntityMapper<TasksDTO, Tasks> {

    @Mapping(source = "assigs.id", target = "assigsId")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    TasksDTO toDto(Tasks tasks);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(source = "assigsId", target = "assigs")
    @Mapping(source = "projectId", target = "project")
    Tasks toEntity(TasksDTO tasksDTO);

    default Tasks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tasks tasks = new Tasks();
        tasks.setId(id);
        return tasks;
    }
}
