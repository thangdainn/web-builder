package org.dainn.mediaservice.config.cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class TempStorageConfig {
    @Bean
    public Path tempStoragePath() throws IOException {
        return Files.createTempDirectory("firebase-uploads");
    }
}
