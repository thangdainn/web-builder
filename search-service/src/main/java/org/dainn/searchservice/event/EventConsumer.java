package org.dainn.searchservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.searchservice.document.User;
import org.dainn.searchservice.dto.user.UserAccessConsumer;
import org.dainn.searchservice.dto.user.UserConsumer;
import org.dainn.searchservice.dto.user.UserInfoConsumer;
import org.dainn.searchservice.service.IUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final IUserService userService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = {"user-created-events", "user-updated-events"},
            groupId = "${spring.kafka.consumer.group-id:search-service-group}",
            concurrency = "${spring.kafka.listener.concurrency:3}"
    )
    public void consumeUserCreatedEvent(@Payload String message) throws JsonProcessingException {
        log.info("User saved event consumed: {}", message);

        UserConsumer dto = objectMapper.readValue(message, UserConsumer.class);
        log.info("User saved event consumed: {}", dto);
        userService.create(convertToUser(dto));
    }

    @KafkaListener(topics = "user-deleted-events", groupId = "${spring.kafka.consumer.group-id:search-service-group}")
    public void consumeUserDeletedEvent(@Payload String message) throws JsonProcessingException {
        log.info("User deleted event consumed: {}", message);

        UserConsumer dto = objectMapper.readValue(message, UserConsumer.class);
        log.info("User deleted event consumed: {}", dto.getId());
        userService.delete(dto.getId());
    }

    @KafkaListener(topics = "user-updated-info-events", groupId = "${spring.kafka.consumer.group-id:search-service-group}")
    public void consumeUserUpdateInfoEvent(@Payload String message) throws JsonProcessingException {
        log.info("User update info event consumed: {}", message);
        UserInfoConsumer dto = objectMapper.readValue(message, UserInfoConsumer.class);
        userService.updateInfo(dto);
    }

    @KafkaListener(topics = "user-updated-access-events", groupId = "${spring.kafka.consumer.group-id:search-service-group}")
    public void consumeUserUpdateAccessEvent(@Payload String message) throws JsonProcessingException {
        log.info("User update access event consumed: {}", message);
        UserAccessConsumer dto = objectMapper.readValue(message, UserAccessConsumer.class);
        userService.updateAccess(dto);
    }

    private User convertToUser(UserConsumer dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .avatarUrl(dto.getAvatarUrl())
                .name(dto.getName())
                .role(dto.getRole())
                .permissions(dto.getPermissions().stream()
                        .map(permission -> User.Permission.builder()
                                .id(permission.getId())
                                .subAccountId(permission.getSubAccountId())
                                .access(permission.getAccess())
                                .build())
                        .toList())
                .subAccounts(dto.getSubAccounts().stream()
                        .map(subAccount -> User.SubAccount.builder()
                                .id(subAccount.getId())
                                .agencyId(subAccount.getAgencyId())
                                .build())
                        .toList())
                .build();
    }
}