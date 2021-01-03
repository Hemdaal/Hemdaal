package main.kotlin.graphql

import com.expedia.graphql.annotations.GraphQLContext
import io.ktor.auth.*
import main.kotlin.auth.JWTTokenManager
import main.kotlin.di.inject
import main.kotlin.models.AuthInfo
import main.kotlin.models.UserInfo
import services.UserService

class System {

    private val userService by lazy { inject<UserService>() }
    private val jwtTokenManager by lazy { inject<JWTTokenManager>() }

    fun user(@GraphQLContext context: GraphQLCallContext): UserInfo? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return userService.getUserBy(email)?.let {
                UserInfo(it, jwtTokenManager.createToken(it) ?: "")
            }
        }
        return null
    }

    fun createUser(
        @GraphQLContext context: GraphQLCallContext,
        name: String,
        email: String,
        password: String
    ): AuthInfo? {
        val status = userService.createUser(name, email, password)
        if (status) {
            return userService.getUserBy(email, password)?.let {
                AuthInfo(jwtTokenManager.createToken(it) ?: "")
            }
        }

        return null
    }

    fun login(
        @GraphQLContext context: GraphQLCallContext,
        email: String,
        password: String
    ): AuthInfo? {
        return userService.getUserBy(email, password)?.let {
            AuthInfo(jwtTokenManager.createToken(it) ?: "")
        }
    }

    fun logout(@GraphQLContext context: GraphQLCallContext): Boolean? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            //TODO invalidate id token by puttin it in logout list.
            return true
        }

        return null
    }

}
