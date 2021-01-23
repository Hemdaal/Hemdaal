package domains.collectors.code.gitlab

data class GitlabProjectResponse(
    val data: GitlabProject
)

data class GitlabProject(
    private val id: String
) {
    fun getProjectId(): Int? {
        return id.replace("gid://gitlab/Project/", "").toIntOrNull()
    }
}
