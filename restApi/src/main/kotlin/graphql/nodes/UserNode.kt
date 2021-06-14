package graphql.nodes

import com.google.gson.annotations.SerializedName
import domains.user.User


data class UserNode(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("jwt_token") val token: String
) {
    constructor(user: User, token: String) : this(user.id, user.name, user.email, token)

    fun projects(): List<ProjectNode> =
        User(id, name, email).getProjects().map {
            ProjectNode(it.key, it.value)
        }

    fun project(projectId: Long): ProjectNode? =
        User(id, name, email).getProject(projectId)?.let {
            ProjectNode(it.first, it.second)
        }

    fun softwareComponent(softwareId: Long): SoftwareComponentNode? =
        User(id, name, email).getSoftwareComponent(softwareId)?.let {
            SoftwareComponentNode(it.id, it.name)
        }

    fun createProject(name: String): ProjectNode {
        val projectScopePair = User(id, name, email).createProject(name)
        return ProjectNode(projectScopePair.first, projectScopePair.second)
    }

    fun projectDashboard(projectId: Long): UserProjectDashboardNode {
        return UserProjectDashboardNode(User(id, name, email).getProjectDashboard(projectId))
    }
}
