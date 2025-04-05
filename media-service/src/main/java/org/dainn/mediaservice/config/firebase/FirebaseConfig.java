package org.dainn.mediaservice.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.service-account-json}")
    private String serviceAccountJson;

    @Value("${firebase.storage-bucket}")
    private String storageBucket;
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(
                serviceAccountJson.getBytes(StandardCharsets.UTF_8)
        );

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setStorageBucket(storageBucket)
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
