package main.kotlin.models

import com.google.gson.annotations.SerializedName
import domains.user.User


data class UserInfo(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("jwt_token") val token: String
) {
    constructor(user: User, token: String) : this(user.id, user.name, user.email, token)

    fun projects(): List<ProjectInfo> =
        User(id, name, email).getProjects().map {
            ProjectInfo(it.key, it.value)
        }

    fun project(projectId: Long): ProjectInfo? =
        User(id, name, email).getProject(projectId)?.let {
            ProjectInfo(it.first, it.second)
        }

    fun createProject(name: String): ProjectInfo {
        val projectScopePair = User(id, name, email).createProject(name)
        return ProjectInfo(projectScopePair.first, projectScopePair.second)
    }

    fun getProjectDashboard(projectId: Long): UserProjectDashboardInfo {
        return UserProjectDashboardInfo(User(id, name, email).getProjectDashboard(projectId))
    }
}
