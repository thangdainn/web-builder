package org.dainn.notificationservice.webclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.response.SubAccountDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class SubAccountWC {
    private final WebClient webClient;

    public SubAccountWC(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("lb://subaccount-service").build();
    }

    @CircuitBreaker(name = "subaccountService", fallbackMethod = "fallbackSA")
    @TimeLimiter(name = "subaccountService")
    @Retry(name = "subaccountService")
    @Bulkhead(name = "subaccountService")
    public Mono<SubAccountDto> getById(String id) {
        return webClient.get()
                .uri("/api/sub-accounts/{id}", id)
                .retrieve()
                .bodyToMono(SubAccountDto.class)
                .onErrorResume(e -> {
                    return Mono.just(new SubAccountDto("Unknown", "unknown"));
                })
                .subscribeOn(Schedulers.boundedElastic()); // Chuyển sang scheduler cho blocking I/O
    }

    public Mono<SubAccountDto> fallbackSA(String id, Throwable t) {
        // Fallback khi circuit breaker mở hoặc retry thất bại
        log.error("Fallback for subaccount service: {}", t.getMessage());
        return Mono.just(new SubAccountDto("unknown", "unknown"));
    }
}
