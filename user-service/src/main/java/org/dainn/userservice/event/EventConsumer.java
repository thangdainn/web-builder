package org.dainn.userservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.mail.MailData;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.service.IMailService;
import org.dainn.userservice.service.IUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final IMailService mailService;
    private final IUserService userService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = {"invite-created-events"},
            groupId = "${spring.kafka.consumer.group-id:invite-group}"
    )
    public void consumeInviteCreatedEvent(@Payload String message) throws JsonProcessingException {
        log.info("Invite saved event consumed: {}", message);

        MailData data = objectMapper.readValue(message, MailData.class);
        UserDto user = userService.findOwnerByAgency(data.getAgencyId());
        data.setInviterName(user.getName());
        mailService.sendEmail(data);
    }
}