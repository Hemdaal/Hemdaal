package utils.network

data class GraphQLRequest(
    private val query: String,
    private val variables: MutableMap<String, String> = mutableMapOf(),
    private val operationName: String = ""
) {

    fun addVariable(key: String, value: String): GraphQLRequest {
        variables[key] = value
        return this
    }

    fun addVariable(key: String, value: Int): GraphQLRequest {
        variables[key] = value.toString()
        return this
    }
}
