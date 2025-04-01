package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.ticket.TicketDto;
import org.dainn.pipelineservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ITicketMapper {
    @Mapping(target = "laneId", source = "lane.id")
    TicketDto toDto(Ticket entity);

    Ticket toEntity(TicketDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Ticket toUpdate(@MappingTarget Ticket entity, TicketDto dto);
}
