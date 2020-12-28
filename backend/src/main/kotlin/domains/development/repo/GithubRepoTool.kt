package domains.development.repo

import domains.development.CodeMetricFilter
import domains.development.Commit
import domains.development.MergeRequest
import domains.development.RepoTool

class GithubRepoTool(
    url: String,
    val token: String?
) : RepoTool(url) {

    override fun collect() {
    }

    override fun getCommits(codeMetricFilter: CodeMetricFilter): List<Commit> {
        return emptyList()
    }

    override fun getMRs(codeMetricFilter: CodeMetricFilter): List<MergeRequest> {
        return emptyList()
    }
}
