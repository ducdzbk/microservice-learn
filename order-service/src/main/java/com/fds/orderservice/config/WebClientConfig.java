package com.fds.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @LoadBalanced
    @Bean
    public WebClient loadBalancedWebClientBuilder(){// WebClient cung cấp khả năng giao tiếp với các dịch vụ web khác
        return WebClient.builder().build();
    }
}
