package org.dainn.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**").permitAll()
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated());
//                .oauth2ResourceServer(oAuth -> oAuth.jwt(Customizer.withDefaults()));
        return http.build();
    }

//    @Bean
//    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
//        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            List<String> roles = jwt.getClaimAsStringList("roles");
//            if (roles == null) {
//                roles = Collections.emptyList();
//            }
//            List<GrantedAuthority> authorities = roles.stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                    .collect(Collectors.toList());
//            converter.setPrincipalClaimName("id");
//            return Flux.fromIterable(authorities);
//        });
//        return converter;
//    }
//
//
//    @Bean
//    public GatewayFilter jwtForwardingFilter() {
//        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .flatMap(authentication -> {
////                    String userId = authentication.getName(); // userId từ principal
//                    String userId = "39395410-e5cd-427e-b875-2aa25684108d"; // userId từ principal
//                    if (userId != null) {
//                        ServerHttpRequest request = exchange.getRequest().mutate()
//                                .header("X-User-Id", userId) // Thêm userId vào header
//                                .build();
//                        return chain.filter(exchange.mutate().request(request).build());
//                    }
//                    return chain.filter(exchange); // Nếu không có userId, tiếp tục filter chain
//                })
//                .switchIfEmpty(chain.filter(exchange)); // Nếu không có Authentication, tiếp tục filter chain
////        }
//
////        return (exchange, chain) -> {
////            ServerHttpRequest request = exchange.getRequest().mutate()
////                    .header(HttpHeaders.AUTHORIZATION, exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
////                    .build();
////            return chain.filter(exchange.mutate().request(request).build());
////        };
//    }
}
