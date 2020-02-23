package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.UserService
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import main.kotlin.graphql.GraphQLCallContext

class UserQuery(private val userService: UserService) {

    fun me(@GraphQLContext context: GraphQLCallContext): UserInfo? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return userService.getUserBy(email)?.let {
                UserInfo(it)
            }
        }
        return null
    }
}