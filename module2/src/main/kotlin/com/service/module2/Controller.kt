package com.service.module2

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api")
class Controller {

    private val logger = LoggerFactory.getLogger(Controller::class.java)

    @PostMapping("create")
    fun create(@RequestBody body: String): Mono<String> {
        logger.info("[REQUEST] create: $body")
        return Mono.just("Received create request with body: $body")
    }

    @DeleteMapping("delete")
    fun delete(): Mono<String> {
        logger.info("[REQUEST] delete")
        return Mono.just("Received delete request")
    }

    @PutMapping("update")
    fun update(@RequestBody body: String): Mono<String> {
        logger.info("[REQUEST] update: $body")
        return Mono.just("Received update request with body: $body")
    }
}
