package com.infra.api_gateway.runner

import com.infra.api_gateway.data.Apps
import com.infra.api_gateway.router.Router
import com.infra.api_gateway.router.RouterHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ApiGatewayRunner {
    @Bean
    fun appRouterFunctions(apps: Apps, routerHandler: RouterHandler): RouterFunction<ServerResponse> {
        return apps.apps.map { app ->
            Router(routerHandler, app).route()
        }.reduce { acc, next ->
            acc.and(next)
        }
    }
}
