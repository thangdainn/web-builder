package org.dainn.notificationservice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

//    @Bean
//    JsonMessageConverter converter(){
//        return new JsonMessageConverter();
//    }

//    @KafkaListener(topics = "notificationTopic")
//    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
////        send out an email notification
//        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
//    }
}