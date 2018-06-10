package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.AssigsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assigs and its DTO AssigsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssigsMapper extends EntityMapper<AssigsDTO, Assigs> {


    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Assigs toEntity(AssigsDTO assigsDTO);

    default Assigs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assigs assigs = new Assigs();
        assigs.setId(id);
        return assigs;
    }
}
