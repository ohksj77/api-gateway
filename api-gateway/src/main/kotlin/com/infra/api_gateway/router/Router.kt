package com.infra.api_gateway.router

import com.infra.api_gateway.data.App
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

class Router(
    private val routerHandler: RouterHandler,
    private val app: App
) {
    private var webClient: WebClient = WebClient.builder()
        .baseUrl(app.createUrl())
        .build()

    fun route(): RouterFunction<ServerResponse> {
        val routes = app.http.routes.map { router ->
            when (router.method) {
                "GET" -> RouterFunctions.route(RequestPredicates.GET(router.path), routerHandler.get(router, webClient))
                "POST" -> RouterFunctions.route(
                    RequestPredicates.POST(router.path),
                    routerHandler.post(router, webClient)
                )

                "DELETE" -> RouterFunctions.route(
                    RequestPredicates.DELETE(router.path),
                    routerHandler.delete(router, webClient)
                )

                "PUT" -> RouterFunctions.route(RequestPredicates.PUT(router.path), routerHandler.put(router, webClient))
                else -> throw IllegalArgumentException("Unsupported method: ${router.method}")
            }
        }
        return routes.reduce { acc, next ->
            acc.and(next)
        }
    }
}
