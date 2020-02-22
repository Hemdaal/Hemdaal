package main.kotlin.auth

import domains.UserService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.auth.session
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.sessions

fun Application.installAuth() {

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
                    val token = JWTTokenManager.createToken(user)
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
                    val email = JWTTokenManager.verifyToken(token)
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

}