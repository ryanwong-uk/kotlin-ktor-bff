package com.rwmobi.kotlinbff

import com.rwmobi.kotlinbff.plugins.configureAdministration
import com.rwmobi.kotlinbff.plugins.configureHTTP
import com.rwmobi.kotlinbff.plugins.configureMonitoring
import com.rwmobi.kotlinbff.plugins.configureRouting
import com.rwmobi.kotlinbff.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureAdministration()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
