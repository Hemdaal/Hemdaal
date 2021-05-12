package main.kotlin.auth

import domains.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject

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
            skipWhen { call -> call.request.headers["Authorization"].isNullOrBlank() }
            validate { credentials ->
                val user = userService.getUserBy(credentials.name, credentials.password)
                if (user != null) {
                    val token = jwtTokenManager.createToken(user)
                    if (token != null) {
                        sessions.set(USER_TOKEN_COOKIE, Session(token))
                        UserIdPrincipal(user.email)
                    } else {
                        null
                    }
                } else {
                    UserIdPrincipal("no_auth")
                }
            }
        }

        jwt(name = JWT_AUTH) {
            realm = REALM
            skipWhen { call -> call.request.headers["Authorization"].isNullOrBlank() }
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
            skipWhen { call -> call.request.headers["Authorization"].isNullOrBlank() }
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
