package org.dainn.apigateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Discovery Server Route
                .route("discovery-server", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                // User Service Route
                .route("user-service", r -> r
                        .path("/api/users/**", "/api/permissions/**", "/api/invitations/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("userService")))
                        .uri("lb://user-service"))

                // Agency Service Route
                .route("agency-service", r -> r
                        .path("/api/agencies/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("agencyService")))
                        .uri("lb://agency-service"))

                // Subscription Service Route
                .route("subscription-service", r -> r
                        .path("/api/subscriptions/**", "/api/addons/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("subscriptionService")))
                        .uri("lb://subscription-service"))

                // Pipeline Service Route
                .route("pipeline-service", r -> r
                        .path("/api/pipelines/**", "/api/lanes/**", "/api/tickets/**", "/api/tags/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("pipelineService")))
                        .uri("lb://pipeline-service"))

                // Funnel Service Route
                .route("funnel-service", r -> r
                        .path("/api/funnels/**", "/api/funnel-pages/**", "/api/class-names/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("funnelService")))
                        .uri("lb://funnel-service"))

                // Media Service Route
                .route("media-service", r -> r
                        .path("/api/medias/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("mediaService")))
                        .uri("lb://media-service"))

                // Notification Service Route
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .removeRequestHeader("Origin")
                                .circuitBreaker(config -> config.setName("notificationService")))
                        .uri("lb://notification-service"))

                // SubAccount Service Route
                .route("subaccount-service", r -> r
                        .path("/api/sub-accounts/**", "/api/contacts/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("subaccountService")))
                        .uri("lb://subaccount-service"))

                // Swagger UI API Docs Routes
                .route("user-service-api-docs", r -> r
                        .path("/user-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/user-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://user-service"))

                .route("subscription-service-api-docs", r -> r
                        .path("/subscription-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/subscription-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://subscription-service"))

                .route("pipeline-service-api-docs", r -> r
                        .path("/pipeline-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/pipeline-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://pipeline-service"))

                .route("funnel-service-api-docs", r -> r
                        .path("/funnel-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/funnel-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://funnel-service"))

                .route("notification-service-api-docs", r -> r
                        .path("/notification-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/notification-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://notification-service"))

                .route("agency-service-api-docs", r -> r
                        .path("/agency-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/agency-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://agency-service"))

                .route("media-service-api-docs", r -> r
                        .path("/media-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/media-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://media-service"))

                .route("subaccount-service-api-docs", r -> r
                        .path("/subaccount-service/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/subaccount-service(?<segment>/?.*)","${segment}"))
                        .uri("lb://subaccount-service"))

                .build();
    }
}