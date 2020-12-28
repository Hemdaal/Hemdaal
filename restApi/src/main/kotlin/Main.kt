package main.kotlin

import InitService
import di.hemdaalInjectionModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
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

    InitService.initDB(
        ApplicationConfig.DB_HOST,
        ApplicationConfig.DB_PORT,
        ApplicationConfig.DB_USER_NAME,
        ApplicationConfig.DB_PASSWORD
    )
    InitService.initCollectors()

    intercept(ApplicationCallPipeline.Features) {
        delay(Duration.ofSeconds(1))
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
