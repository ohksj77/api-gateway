package com.service.module1

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api")
class Controller {

    private val logger = LoggerFactory.getLogger(Controller::class.java)

    @GetMapping("query")
    fun query(@RequestParam name: String, @RequestParam age: Int): Mono<String> {
        logger.info("[REQUEST] name: $name, age: $age")
        return Mono.just("Received query request with name: $name and age: $age")
    }

    @GetMapping("user/{id}")
    fun getOne(@PathVariable id: Long): Mono<String> {
        logger.info("[REQUEST] id: $id")
        return Mono.just("Received getOne request with id: $id")
    }
}
