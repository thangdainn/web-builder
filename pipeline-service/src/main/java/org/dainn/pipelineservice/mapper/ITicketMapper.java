package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.TicketDto;
import org.dainn.pipelineservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITicketMapper {
    @Mapping(target = "laneId", source = "lane.id")
    TicketDto toDto(Ticket entity);

    Ticket toEntity(TicketDto dto);
}
