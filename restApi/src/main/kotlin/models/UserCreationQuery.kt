package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.UserService
import main.kotlin.auth.JWTTokenManager
import main.kotlin.graphql.GraphQLCallContext

class UserCreationQuery(
    private val userService: UserService,
    private val jwtTokenManager: JWTTokenManager
) {

    fun createUser(
        @GraphQLContext context: GraphQLCallContext,
        name: String,
        email: String,
        password: String
    ): Boolean {
        return userService.createUser(name, email, password)
    }

    fun login(
        @GraphQLContext context: GraphQLCallContext,
        email: String,
        password: String
    ): String? {
        val user = userService.getUserBy(email, password)
        return user?.let {
            jwtTokenManager.createToken(user)
        }
    }
}