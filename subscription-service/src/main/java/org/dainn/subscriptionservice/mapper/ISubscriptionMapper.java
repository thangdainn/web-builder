package org.dainn.subscriptionservice.mapper;

import org.dainn.subscriptionservice.dto.SubscriptionDto;
import org.dainn.subscriptionservice.dto.event.SubscriptionResp;
import org.dainn.subscriptionservice.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISubscriptionMapper {
    SubscriptionDto toDto(Subscription entity);
    SubscriptionDto toDto(SubscriptionResp event);
    Subscription toEntity(SubscriptionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Subscription updateEntity(@MappingTarget Subscription entity, SubscriptionDto dto);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "price", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SubscriptionDto toUpdate(@MappingTarget SubscriptionDto target, SubscriptionResp dto);
}
