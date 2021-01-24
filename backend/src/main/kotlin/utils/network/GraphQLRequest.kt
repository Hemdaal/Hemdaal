package utils.network

data class GraphQLRequest(
    private val operationName: String,
    private val query: String,
    private val variables: MutableMap<String, String> = mutableMapOf()
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
