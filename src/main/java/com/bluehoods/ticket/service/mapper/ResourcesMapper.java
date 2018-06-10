package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.ResourcesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resources and its DTO ResourcesDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AssigsMapper.class, TasksMapper.class})
public interface ResourcesMapper extends EntityMapper<ResourcesDTO, Resources> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "assigs.id", target = "assigsId")
    @Mapping(source = "tasks.id", target = "tasksId")
    ResourcesDTO toDto(Resources resources);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "assigsId", target = "assigs")
    @Mapping(source = "tasksId", target = "tasks")
    Resources toEntity(ResourcesDTO resourcesDTO);

    default Resources fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resources resources = new Resources();
        resources.setId(id);
        return resources;
    }
}
