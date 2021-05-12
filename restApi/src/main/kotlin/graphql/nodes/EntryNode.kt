package main.kotlin.graphql.nodes

import com.expedia.graphql.annotations.GraphQLContext
import domains.UserService
import graphql.nodes.AuthNode
import graphql.nodes.UserNode
import io.ktor.auth.*
import main.kotlin.auth.JWTTokenManager
import main.kotlin.di.inject
import main.kotlin.graphql.GraphQLCallContext

class EntryNode {

    private val system by lazy { inject<UserService>() }
    private val jwtTokenManager by lazy { inject<JWTTokenManager>() }

    fun user(@GraphQLContext context: GraphQLCallContext): UserNode? {
        val email = context.call.principal<UserIdPrincipal>()?.name
        if (email != null) {
            return system.getUserBy(email)?.let {
                UserNode(it, jwtTokenManager.createToken(it) ?: "")
            }
        }
        return null
    }

    fun createUser(
        @GraphQLContext context: GraphQLCallContext,
        name: String,
        email: String,
        password: String
    ): AuthNode? {
        val status = system.createUser(name, email, password)
        if (status) {
            return system.getUserBy(email, password)?.let {
                AuthNode(jwtTokenManager.createToken(it) ?: "")
            }
        }

        return null
    }

    fun login(
        @GraphQLContext context: GraphQLCallContext,
        email: String,
        password: String
    ): AuthNode? {
        return system.getUserBy(email, password)?.let {
            AuthNode(jwtTokenManager.createToken(it) ?: "")
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
