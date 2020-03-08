package main.kotlin

import InitService
import di.hemdaalInjectionModule
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.time.delay
import main.kotlin.auth.installAuth
import main.kotlin.di.injectionModule
import main.kotlin.graphql.installGraphQL
import org.koin.ktor.ext.Koin
import java.time.Duration

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        watchPaths = listOf(""),
        port = 8080,
        module = Application::module
    ).apply {
        start(wait = true)
    }
}

fun Application.module() {
    install(CORS) {
        method(HttpMethod.Post)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.Referrer)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.Accept)
        header(HttpHeaders.UserAgent)
        allowCredentials = true
        anyHost()
    }
    InitService().init(
        ApplicationConfig.DB_HOST,
        ApplicationConfig.DB_PORT,
        ApplicationConfig.DB_USER_NAME,
        ApplicationConfig.DB_PASSWORD
    )
    intercept(ApplicationCallPipeline.Features) {
        delay(Duration.ofSeconds(2))
    }

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