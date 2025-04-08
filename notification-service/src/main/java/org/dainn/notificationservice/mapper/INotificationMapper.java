package org.dainn.notificationservice.mapper;

import org.dainn.notificationservice.dto.NotificationDetailDto;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface INotificationMapper {
    NotificationDto toDto(Notification entity);

    Notification toEntity(NotificationDto dto);

    NotificationDetailDto toDetailDto(Notification dto);
}
