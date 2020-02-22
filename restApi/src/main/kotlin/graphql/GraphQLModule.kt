package main.kotlin.graphql

import com.expedia.graphql.SchemaGeneratorConfig
import com.expedia.graphql.TopLevelObject
import com.expedia.graphql.toSchema
import domains.UserService
import graphql.ExecutionInput
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.content.defaultResource
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

data class GraphQLRequest(val query: String, val operationName: String, val variables: Map<String, Any>)

fun Application.installGraphQL() {
    val userService = UserService()

    val config = SchemaGeneratorConfig(listOf("main.kotlin.models"))
    val queries = listOf(TopLevelObject(UserQuery(userService)))
    val schema: GraphQLSchema = toSchema(config = config, queries = queries)
    val graphQL = GraphQL.newGraphQL(schema).build()

    suspend fun ApplicationCall.executeQuery() {
        val request = receive<GraphQLRequest>()
        val executionInput = ExecutionInput.newExecutionInput()
            .context(GraphQLCallContext(this))
            .query(request.query)
            .operationName(request.operationName)
            .variables(request.variables)
            .build()

        graphQL.execute(executionInput)

        respond(graphQL.execute(executionInput))
    }

    routing {
        authenticate("session_auth") {
            post("/graphql") {
                call.executeQuery()
            }

            get("/graphql") {
                call.executeQuery()
            }
        }

        static("/graphql-playground") {
            defaultResource("static/graphql/playground.html")
        }
    }
}