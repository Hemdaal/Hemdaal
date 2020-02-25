package collectors

import domains.metric.repo.Commit

data class RepoData(
    val repoName: String,
    val commits: List<Commit>
)