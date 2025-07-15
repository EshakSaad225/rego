package com.penta.penta_service_posts.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.penta.penta_service_posts.domain")
@EnableJpaRepositories("com.penta.penta_service_posts.repos")
@EnableTransactionManagement
public class DomainConfig {
}