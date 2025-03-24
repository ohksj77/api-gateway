package com.infra.api_gateway.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfig(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    @Bean
    fun circuitBreaker(): CircuitBreaker {
        return circuitBreakerRegistry.circuitBreaker("default")
    }
}
