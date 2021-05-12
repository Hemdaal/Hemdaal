package domains.codeManagement.repo

class GithubRepoTool(
    url: String,
    softwareId: Long,
    val token: String?
) : RepoTool(url, softwareId) {

    override fun collect() {
    }
}
