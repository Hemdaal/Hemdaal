package main.kotlin.graphql

import com.expedia.graphql.annotations.GraphQLContext
import domains.System
import io.ktor.auth.*
import main.kotlin.auth.JWTTokenManager
import main.kotlin.di.inject
import main.kotlin.models.AuthInfo
import main.kotlin.models.UserInfo

class Entry {

    private val system by lazy { inject<System>() }
    private val jwtTokenManager by lazy { inject<JWTTokenManager>() }

    fun user(@GraphQLContext context: GraphQLCallContext): UserInfo? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return system.getUserBy(email)?.let {
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
        val status = system.createUser(name, email, password)
        if (status) {
            return system.getUserBy(email, password)?.let {
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
        return system.getUserBy(email, password)?.let {
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
