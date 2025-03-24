package com.infra.api_gateway.router

import com.infra.api_gateway.data.Route
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RouterHandler(
    private val circuitBreaker: CircuitBreaker,
) {
    private val logger = LoggerFactory.getLogger(RouterHandler::class.java)

    fun get(route: Route, webClient: WebClient): (ServerRequest) -> Mono<ServerResponse> = { request ->
        logger.info("[REQUEST] get")
        val uri = buildUri(route.path, request)
        val queryParams = request.queryParams().toSingleValueMap()
        val requestBuilder = webClient.get()
            .uri { builder ->
                builder.path(uri)
                queryParams.forEach { (key, value) -> builder.queryParam(key, value) }
                builder.build()
            }

        route.header?.forEach { (key, value) -> requestBuilder.header(key, value) }

        requestBuilder.retrieve()
            .bodyToMono(String::class.java)
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .flatMap { ServerResponse.ok().bodyValue(it) }
    }

    private fun buildUri(path: String, request: ServerRequest): String {
        var uri = path
        request.pathVariables().forEach { (key, value) ->
            uri = uri.replace("{$key}", value)
        }
        return uri
    }

    fun post(route: Route, webClient: WebClient): (ServerRequest) -> Mono<ServerResponse> = { request ->
        logger.info("[REQUEST] post")
        request.bodyToMono(String::class.java).flatMap { body ->
            val requestBuilder = webClient.post().uri(route.path)
            route.header?.forEach { (key, value) -> requestBuilder.header(key, value) }
            requestBuilder.bodyValue(body)
                .retrieve()
                .bodyToMono(String::class.java)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .flatMap { ServerResponse.ok().bodyValue(it) }
        }
    }

    fun delete(route: Route, webClient: WebClient): (ServerRequest) -> Mono<ServerResponse> = { request ->
        logger.info("[REQUEST] delete")
        val requestBuilder = webClient.delete().uri(route.path)
        route.header?.forEach { (key, value) -> requestBuilder.header(key, value) }

        requestBuilder.retrieve()
            .bodyToMono(String::class.java)
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .flatMap { ServerResponse.ok().bodyValue(it) }
    }

    fun put(route: Route, webClient: WebClient): (ServerRequest) -> Mono<ServerResponse> = { request ->
        logger.info("[REQUEST] put")
        request.bodyToMono(String::class.java).flatMap { body ->
            val requestBuilder = webClient.put().uri(route.path)
            route.header?.forEach { (key, value) -> requestBuilder.header(key, value) }
            requestBuilder.bodyValue(body)
                .retrieve()
                .bodyToMono(String::class.java)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .flatMap { ServerResponse.ok().bodyValue(it) }
        }
    }
}
