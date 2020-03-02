package main.kotlin.graphql

import com.expedia.graphql.SchemaGeneratorConfig
import com.expedia.graphql.TopLevelObject
import com.expedia.graphql.toSchema
import graphql.ExecutionInput
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.content.defaultResource
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import main.kotlin.auth.BASIC_AUTH
import main.kotlin.auth.JWT_AUTH
import main.kotlin.auth.SESSION_AUTH
import org.koin.ktor.ext.inject

data class GraphQLRequest(val query: String?, val operationName: String?, val variables: Map<String, Any>?)

fun Application.installGraphQL() {
    val authenticatedUserQuery: AuthenticatedUserQuery by inject()
    val userCreationQuery: UserCreationQuery by inject()

    val config = SchemaGeneratorConfig(listOf("main.kotlin.models"))
    val authenticatedQueries = listOf(
        TopLevelObject(
            authenticatedUserQuery
        )
    )
    val userCreationQueries = listOf(
        TopLevelObject(
            userCreationQuery
        )
    )

    val authenticatedSchema: GraphQLSchema =
        toSchema(config = config, queries = authenticatedQueries, mutations = authenticatedQueries)
    val userCreationSchema: GraphQLSchema =
        toSchema(config = config, queries = userCreationQueries, mutations = userCreationQueries)
    val authenticatedGraphQL = GraphQL.newGraphQL(authenticatedSchema).build()
    val userCreationGraphQL = GraphQL.newGraphQL(userCreationSchema).build()

    suspend fun ApplicationCall.executeAuthenticatedQuery() {
        val request = receive<GraphQLRequest>()
        val executionInput = ExecutionInput.newExecutionInput()
            .context(GraphQLCallContext(this))
            .query(request.query)
            .operationName(request.operationName)
            .variables(request.variables)
            .build()

        respond(authenticatedGraphQL.execute(executionInput))
    }

    suspend fun ApplicationCall.executeQuery() {
        val request = receive<GraphQLRequest>()
        val executionInput = ExecutionInput.newExecutionInput()
            .context(GraphQLCallContext(this))
            .query(request.query)
            .operationName(request.operationName)
            .variables(request.variables)
            .build()

        respond(userCreationGraphQL.execute(executionInput))
    }

    routing {
        authenticate(BASIC_AUTH, SESSION_AUTH, JWT_AUTH) {
            post("/graphql") {
                val name = call.principal<UserIdPrincipal>()?.name
                if (name == "no_auth") {
                    call.executeQuery()
                } else {
                    call.executeAuthenticatedQuery()
                }
            }
        }

        static("/graphql-playground") {
            defaultResource("static/graphql/playground.html")
        }
    }
}