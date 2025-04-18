package org.dainn.mediaservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, FileMessage> kafkaTemplate;

    @Value("${kafka.topic.upload-events}")
    private String uploadEventsTopic;

    public void sendUploadEvent(FileMessage message) {
        try {
            kafkaTemplate.send(uploadEventsTopic, message.getFileName(), message);
            log.info("File {} sent successfully", message.getFileName());
        } catch (Exception e) {
            log.error("Failed to send file", e);
        }
    }
}
