package main.kotlin

import InitService
import di.hemdaalInjectionModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import main.kotlin.auth.installAuth
import main.kotlin.di.injectionModule
import main.kotlin.graphql.installGraphQL
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        watchPaths = listOf(""),
        port = 8080,
        module = Application::module
    ).apply { start(wait = true) }
}

fun Application.module() {

    InitService().init(
        ApplicationConfig.DB_HOST,
        ApplicationConfig.DB_PORT,
        ApplicationConfig.DB_USER_NAME,
        ApplicationConfig.DB_PASSWORD
    )

    install(DefaultHeaders)
    install(CallLogging)
    install(Koin) {
        modules(listOf(injectionModule, hemdaalInjectionModule))
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    installAuth()
    installGraphQL()

    routing {
        get("/") {
            call.respondText("Hemdaal API Working! Success.", ContentType.Text.Plain)
        }
    }
}