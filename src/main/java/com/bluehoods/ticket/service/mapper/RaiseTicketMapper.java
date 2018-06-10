package com.bluehoods.ticket.service.mapper;

import com.bluehoods.ticket.domain.*;
import com.bluehoods.ticket.service.dto.RaiseTicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RaiseTicket and its DTO RaiseTicketDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, UserMapper.class})
public interface RaiseTicketMapper extends EntityMapper<RaiseTicketDTO, RaiseTicket> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.login", target = "assignedToLogin")
    RaiseTicketDTO toDto(RaiseTicket raiseTicket);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "assignedToId", target = "assignedTo")
    RaiseTicket toEntity(RaiseTicketDTO raiseTicketDTO);

    default RaiseTicket fromId(Long id) {
        if (id == null) {
            return null;
        }
        RaiseTicket raiseTicket = new RaiseTicket();
        raiseTicket.setId(id);
        return raiseTicket;
    }
}
