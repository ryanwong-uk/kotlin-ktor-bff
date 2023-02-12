package com.rwmobi.kotlinbff.plugins

import com.codahale.metrics.Slf4jReporter
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.metrics.dropwizard.DropwizardMetrics
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.path
import java.util.concurrent.TimeUnit
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    install(DropwizardMetrics) {
        Slf4jReporter.forRegistry(registry)
            //.outputTo(this@module.log)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start(10, TimeUnit.SECONDS)
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
        callIdMdc("call-id")
    }
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
}
