package domains.development.repo

import domains.development.RepoTool

class GithubRepoTool(
    url: String,
    softwareId: Long,
    val token: String?
) : RepoTool(url, softwareId) {

    override fun collect() {
    }
}
