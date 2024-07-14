package com.piattaforme.edilgiulianiback.securityconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain serverfilterchain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin")
                        .hasRole("admin")
                        .requestMatchers("/*")
                        .hasRole("user")
                        .requestMatchers(
                                antMatcher("/hello/*")
                        ).permitAll()
                        .anyRequest().authenticated()).oauth2Login();
        //http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }
}

