package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.login", target = "assignedToLogin")
    ProjectDTO toDto(Project project);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(source = "assignedToId", target = "assignedTo")
    @Mapping(target = "issueTickets", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
