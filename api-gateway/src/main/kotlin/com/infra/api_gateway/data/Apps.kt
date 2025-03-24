package com.infra.api_gateway.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "apps")
class Apps {
    lateinit var apps: List<App>
}
