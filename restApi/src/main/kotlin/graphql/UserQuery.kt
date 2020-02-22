package main.kotlin.graphql

import com.expedia.graphql.annotations.GraphQLContext
import domains.UserService
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import main.kotlin.models.User

class UserQuery(private val userService: UserService) {

    fun getUser(id: Long): User? {
        return userService.getUserBy("")?.let { User(it) }
    }

    fun me(@GraphQLContext context: GraphQLCallContext): User? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return userService.getUserBy(email)?.let { User(it) }
        }
        return null
    }
}