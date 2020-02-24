package main.kotlin.graphql

import com.expedia.graphql.annotations.GraphQLContext
import domains.UserService
import main.kotlin.auth.JWTTokenManager
import main.kotlin.models.UserInfo

class UserCreationQuery(
    private val userService: UserService,
    private val jwtTokenManager: JWTTokenManager
) {

    fun createUser(
        @GraphQLContext context: GraphQLCallContext,
        name: String,
        email: String,
        password: String
    ): UserInfo? {
        val status = userService.createUser(name, email, password)
        if (status) {
            return userService.getUserBy(email, password)?.let {
                UserInfo(it, jwtTokenManager.createToken(it) ?: "")
            }
        }

        return null
    }

    fun login(
        @GraphQLContext context: GraphQLCallContext,
        email: String,
        password: String
    ): UserInfo? {
        return userService.getUserBy(email, password)?.let {
            UserInfo(it, jwtTokenManager.createToken(it) ?: "")
        }
    }
}