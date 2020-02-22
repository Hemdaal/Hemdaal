package main.kotlin.auth

import domains.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.auth.session
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.sessions
import main.kotlin.models.LoginInfo
import main.kotlin.models.SignupInfo
import main.kotlin.models.TokenInfo
import org.koin.ktor.ext.inject

fun Application.installAuth() {

    val userService: UserService by inject()
    val jwtTokenManager: JWTTokenManager by inject()

    install(Sessions) {
        cookie<Session>("user_session") {
            // Be sure to give the whole domain access to the cookie
            cookie.path = "/"
        }
    }
    install(Authentication) {
        basic(name = "password_auth") {
            realm = "hemdaal_server"
            validate { credentials ->
                val user = UserService().getUserBy(credentials.name, credentials.password)
                if (user != null) {
                    val token = jwtTokenManager.createToken(user)
                    sessions.set("token", token)
                    UserIdPrincipal(user.email)
                } else {
                    null
                }
            }
        }

        //JWT Based session.
        session<Session>("session_auth") {
            validate {
                val session: Session? = sessions.get("token") as Session?

                if (session != null) {
                    val token = session.jwtToken
                    val email = jwtTokenManager.verifyToken(token)
                    if (email != null) {
                        UserIdPrincipal(email)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        }
    }

    routing {
        post("/signup") {
            val signupInfo = call.receive<SignupInfo>()
            val status = userService.createUser(signupInfo.name, signupInfo.email, signupInfo.password)
            if (status) {
                call.respond(HttpStatusCode.Created)
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }
        post("/login") {
            val loginInfo = call.receive<LoginInfo>()
            val user = userService.getUserBy(loginInfo.email, loginInfo.password)
            val token = user?.let {
                jwtTokenManager.createToken(user)
            }

            if (token != null) {
                call.respond(TokenInfo(token))
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}