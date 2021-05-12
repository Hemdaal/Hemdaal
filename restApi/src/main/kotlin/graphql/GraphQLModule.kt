package main.kotlin.graphql

import com.expedia.graphql.SchemaGeneratorConfig
import com.expedia.graphql.TopLevelObject
import com.expedia.graphql.toSchema
import graphql.ExecutionInput
import graphql.GraphQL
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import main.kotlin.auth.BASIC_AUTH
import main.kotlin.auth.JWT_AUTH
import main.kotlin.auth.SESSION_AUTH
import main.kotlin.graphql.nodes.EntryNode

data class GraphQLRequest(val query: String?, val operationName: String?, val variables: Map<String, Any>?)

fun Application.installGraphQL() {

    val config = SchemaGeneratorConfig(listOf("graphql.nodes"))
    val queries = listOf(TopLevelObject(EntryNode()))
    val graphQL = GraphQL.newGraphQL(toSchema(config = config, queries = queries, mutations = queries)).build()

    suspend fun ApplicationCall.executeQuery() {
        val request = receive<GraphQLRequest>()
        val executionInput = ExecutionInput.newExecutionInput()
            .context(GraphQLCallContext(this))
            .query(request.query)
            .operationName(request.operationName)
            .variables(request.variables)
            .build()

        respond(graphQL.execute(executionInput))
    }

    routing {
        authenticate(BASIC_AUTH, SESSION_AUTH, JWT_AUTH) {
            post("/graphql") {
                call.executeQuery()
            }
        }

        static("/playground") {
            defaultResource("static/graphql/playground.html")
        }
    }
}
