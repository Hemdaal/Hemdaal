package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import com.google.gson.annotations.SerializedName
import domains.User
import main.kotlin.graphql.GraphQLCallContext


data class UserInfo(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("jwt_token") val token: String
) {
    constructor(user: User, token: String) : this(user.id, user.name, user.email, token)

    fun projects(@GraphQLContext context: GraphQLCallContext): List<ProjectInfo> =
        User(id, name, email).getProjects().map {
            ProjectInfo(it.key, it.value)
        }


    fun project(@GraphQLContext context: GraphQLCallContext, projectId: Long): ProjectInfo? =
        User(id, name, email).getProject(projectId)?.let {
            ProjectInfo(it.first, it.second)
        }

    fun createProject(@GraphQLContext context: GraphQLCallContext, name: String): ProjectInfo {
        val projectScopePair = User(id, name, email).createProject(name)
        return ProjectInfo(projectScopePair.first, projectScopePair.second)
    }

}