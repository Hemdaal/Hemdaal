package main.kotlin.graphql

import com.expedia.graphql.annotations.GraphQLContext
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import main.kotlin.auth.JWTTokenManager
import main.kotlin.models.UserInfo
import services.UserService

class AuthenticatedUserQuery(
    private val userService: UserService,
    private val jwtTokenManager: JWTTokenManager
) {

    fun me(@GraphQLContext context: GraphQLCallContext): UserInfo? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return userService.getUserBy(email)?.let {
                UserInfo(it, jwtTokenManager.createToken(it) ?: "")
            }
        }
        return null
    }
}