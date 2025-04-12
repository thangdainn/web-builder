package org.dainn.notificationservice.webclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j
public class UserWC {
    private final WebClient webClient;

    public UserWC(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("lb://user-service").build();
    }

//    @Retry(name = "userRetry")
    @Bulkhead(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @TimeLimiter(name = "userService")
    public Mono<UserDto> getById(String id) {
        return webClient.get()
                .uri("/api/users/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class)
                .doOnSuccess(user -> log.info("Successfully called User Service for userId: {}", id))
                .doOnError(error -> {
                    log.error("Failed to call User Service for userId: {}, error: {}", id, error.getMessage());
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(300))
                        .doBeforeRetry(retrySignal -> log.info("Retrying... Attempt: {}", retrySignal.totalRetries()))
                        .doAfterRetry(retrySignal -> log.warn("After retrying... Attempt: {}", retrySignal.totalRetries()))
                )
                .subscribeOn(Schedulers.boundedElastic());
    }
    public Mono<UserDto> fallbackUser(String id, Throwable t) {
        log.error("Fallback for user service: {}", t.getMessage());
        return Mono.just(new UserDto("id", "unknown", "unknown", "unknown", "unknown", "unknown"));
    }
}
