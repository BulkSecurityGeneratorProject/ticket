package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.RolesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Roles and its DTO RolesDTO.
 */
@Mapper(componentModel = "spring", uses = {AssigsMapper.class, TasksMapper.class})
public interface RolesMapper extends EntityMapper<RolesDTO, Roles> {

    @Mapping(source = "assigs.id", target = "assigsId")
    @Mapping(source = "tasks.id", target = "tasksId")
    RolesDTO toDto(Roles roles);

    @Mapping(source = "assigsId", target = "assigs")
    @Mapping(source = "tasksId", target = "tasks")
    Roles toEntity(RolesDTO rolesDTO);

    default Roles fromId(Long id) {
        if (id == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setId(id);
        return roles;
    }
}
