package com.service.module1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Module1Application

fun main(args: Array<String>) {
	runApplication<Module1Application>(*args)
}
