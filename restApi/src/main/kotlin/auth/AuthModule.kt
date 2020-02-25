package main.kotlin.auth

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.auth.jwt.jwt
import io.ktor.auth.session
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.sessions
import org.koin.ktor.ext.inject
import services.UserService

const val BASIC_AUTH = "basic_auth"
const val JWT_AUTH = "jwt_auth"
const val SESSION_AUTH = "session_auth"
const val REALM = "hemdaal_server"
const val USER_TOKEN_COOKIE = "user_token_cookie"

fun Application.installAuth() {

    val userService: UserService by inject()
    val jwtTokenManager: JWTTokenManager by inject()

    install(Sessions) {
        cookie<Session>(USER_TOKEN_COOKIE) {
            // Be sure to give the whole domain access to the cookie
            cookie.path = "/"
        }
    }
    install(Authentication) {
        basic(name = BASIC_AUTH) {
            realm = REALM
            validate { credentials ->
                val user = UserService().getUserBy(credentials.name, credentials.password)
                if (user != null) {
                    val token = jwtTokenManager.createToken(user)
                    if (token != null) {
                        sessions.set(USER_TOKEN_COOKIE, Session(token))
                        UserIdPrincipal(user.email)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        }

        jwt(name = JWT_AUTH) {
            realm = REALM
            verifier(jwtTokenManager.verifier)
            validate { credential ->
                val email = jwtTokenManager.getEmailFromJwt(credential.payload.claims)
                email?.let {
                    UserIdPrincipal(it)
                }
            }
        }

        //JWT Based session.
        session<Session>(name = SESSION_AUTH) {
            validate {
                val session: Session? = sessions.get(USER_TOKEN_COOKIE) as Session?

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
}