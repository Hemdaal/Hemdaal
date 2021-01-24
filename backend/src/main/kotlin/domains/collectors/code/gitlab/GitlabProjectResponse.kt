package domains.collectors.code.gitlab

data class GitlabProjectResponse(
    val data: GitlabProjectResponseData
)

data class GitlabProjectResponseData(
    val project: GitlabProject
)

data class GitlabProject(
    private val id: String
) {
    fun getProjectId(): Int? {
        return id.replace("gid://gitlab/Project/", "").toIntOrNull()
    }
}
