package org.dainn.mediaservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.mediaservice.util.constant.KafkaConstant;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEvent(NotificationEvent notification) throws Exception {
        String eventJson = objectMapper.writeValueAsString(notification);
        kafkaTemplate.send(KafkaConstant.MEDIA_EVENTS_TOPIC, eventJson);
        log.info("Sent media notification: {}", eventJson);
    }
}
