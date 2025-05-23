package org.dainn.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**").permitAll()
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth -> oAuth
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }


    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> rolesMap = jwt.getClaimAsMap("roles");
            List<String> roles;
            if (rolesMap != null && rolesMap.containsKey("role")) {
                Object role = rolesMap.get("role");
                if (role instanceof String) {
                    roles = List.of((String) role);
                } else {
                    roles = Collections.emptyList();
                }
            } else {
                roles = Collections.emptyList();
            }
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toList());
            converter.setPrincipalClaimName("id");
            return Flux.fromIterable(authorities);
        });
        return converter;
    }
}
