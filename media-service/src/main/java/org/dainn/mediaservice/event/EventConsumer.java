package org.dainn.mediaservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.dainn.mediaservice.service.IMediaService;
import org.dainn.mediaservice.service.impl.S3Service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final S3Service s3Service;
    private final IMediaService mediaService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "subaccount-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteSubAccount(@Payload String message) {
        try {
            String subAccountId = objectMapper.readValue(message, String.class);
            mediaService.deleteBySA(subAccountId);
            log.info("Sub-account {} deleted successfully", subAccountId);
        } catch (Exception e) {
            log.error("Failed to delete sub-account: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "upload-events", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveFile(ConsumerRecord<String, FileMessage> record) {
        try {
            FileMessage message = record.value();
            Path tempFilePath = Paths.get(message.getTempFilePath());
            if (Files.exists(tempFilePath)) {
                byte[] fileContent = Files.readAllBytes(tempFilePath);

                MultipartFile multipartFile = new CustomMultipartFile(
                        fileContent,
                        message.getFileName(),
                        message.getContentType()
                );

                s3Service.upload(multipartFile, message.getFileName());

                Files.deleteIfExists(tempFilePath);

                log.info("File {} received and saved successfully", message.getFileName());
            } else {
                log.error("File {} does not exist at path {}", message.getFileName(), message.getTempFilePath());
            }
        } catch(Exception e){
            log.error("Failed to process received file: {}", e.getMessage());
        }
    }

    private static class CustomMultipartFile implements MultipartFile {
        private final byte[] content;
        private final String name;
        private final String originalFilename;
        private final String contentType;

        public CustomMultipartFile(byte[] content, String name, String contentType) {
            this.content = content;
            this.name = name;
            this.originalFilename = name;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IllegalStateException, IOException {
            Files.write(dest.toPath(), content);
        }
    }
}