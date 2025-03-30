package org.dainn.subscriptionservice.mapper;

import org.dainn.subscriptionservice.dto.SubscriptionDto;
import org.dainn.subscriptionservice.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISubscriptionMapper {
    SubscriptionDto toDto(Subscription entity);
    Subscription toEntity(SubscriptionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Subscription updateEntity(@MappingTarget Subscription entity, SubscriptionDto dto);
}
