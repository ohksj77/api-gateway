package com.infra.api_gateway.data

class App(
    val port: Int,
    val version: String,
    val name: String,
    val http: Http
) {
    fun createUrl(): String {
        return http.baseUrl + ":" + port
    }
}

class Http(
    val baseUrl: String,
    val routes: List<Route>
)

class Route(
    val method: String,
    val path: String,
    val header: Map<String, String>?
)
