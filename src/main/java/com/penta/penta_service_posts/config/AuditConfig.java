package com.penta.penta_service_posts.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.penta.penta_service_posts.Converter.SpringSecurityAuditorAware;
import com.penta.penta_service_posts.domain.Users;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    private final SpringSecurityAuditorAware auditorAware;

    public AuditConfig(SpringSecurityAuditorAware auditorAware) {
        this.auditorAware = auditorAware;
    }

    @Bean
    public AuditorAware<Users> auditorProvider() {
        return auditorAware;
    }
}