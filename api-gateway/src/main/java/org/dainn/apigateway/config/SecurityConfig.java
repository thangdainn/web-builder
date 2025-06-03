package org.dainn.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String[] SERVICES = {
            "user-service",
            "agency-service",
            "subscription-service",
            "pipeline-service",
            "funnel-service",
            "media-service",
            "notification-service",
            "search-service",
            "payment-service"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String[] apiDocsPatterns = Arrays.stream(SERVICES)
                .map(service -> "/" + service + "/v3/api-docs/**")
                .toArray(String[]::new);
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                                .pathMatchers("/eureka/**", "/**", "/v3/api-docs/**", "/api/users/sync", "/api/payments/**").permitAll()
                                .pathMatchers(apiDocsPatterns).permitAll()

                                // User Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/users/**", "/api/permissions/**", "/api/invitations/pending/**", "/api/sub-accounts/agency/**", "/api/contacts/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER", "ROLE_SUBACCOUNT_GUEST")

                                .pathMatchers(HttpMethod.POST, "/api/users/**", "/api/permissions", "/api/invitations", "/api/sub-accounts")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

                                .pathMatchers(HttpMethod.PUT, "/api/users/**", "/api/permissions/**", "/api/sub-accounts/**", "/api/contacts/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.DELETE, "/api/invitations/**", "/api/sub-accounts", "/api/contacts")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

                                // Search Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/search/users/sub-members/**", "/api/search/users/members/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

                                // Notification Service Endpoints
                                .pathMatchers("/api/notifications/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                // Media Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/medias/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER", "ROLE_SUBACCOUNT_GUEST")

                                .pathMatchers(HttpMethod.POST, "/api/medias/upload", "/api/medias/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.DELETE, "/api/medias/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                // Funnel Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/funnels/**", "/api/funnel-pages/**", "/api/class-names/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER", "ROLE_SUBACCOUNT_GUEST")

                                .pathMatchers(HttpMethod.POST, "/api/funnels/**", "/api/funnel-pages/**", "/api/class-names/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.PUT, "/api/funnels/**", "/api/funnel-pages/**", "/api/class-names/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.DELETE, "/api/funnels/**", "/api/funnel-pages/**", "/api/class-names/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                // Agency Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/agencies/**", "/api/agency-sos/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

//                        .pathMatchers(HttpMethod.POST, "/api/agencies/**", "/api/agency-sos/**")
//                        .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

                                .pathMatchers(HttpMethod.PUT, "/api/agencies/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN")

                                .pathMatchers(HttpMethod.DELETE, "/api/agencies/**", "/api/agency-sos/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER")

                                // Pipeline Service Endpoints
                                .pathMatchers(HttpMethod.GET, "/api/pipelines/**", "/api/lanes/**", "/api/tickets/**", "/api/tags/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.POST, "/api/pipelines/**", "/api/lanes/**", "/api/tickets/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.PUT, "/api/pipelines/**", "/api/lanes/**", "/api/tickets/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .pathMatchers(HttpMethod.DELETE, "/api/pipelines/**", "/api/lanes/**", "/api/tickets/**")
                                .hasAnyAuthority("ROLE_AGENCY_OWNER", "ROLE_AGENCY_ADMIN", "ROLE_SUBACCOUNT_USER")

                                .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth -> oAuth
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }


    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = jwt.getClaimAsString("role");
            List<GrantedAuthority> authorities;
            if (role != null && !role.isEmpty()) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            } else {
                authorities = Collections.emptyList();
            }
            converter.setPrincipalClaimName("id");
            return Flux.fromIterable(authorities);
        });
        return converter;
    }
}
