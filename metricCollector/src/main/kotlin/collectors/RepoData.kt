package collectors

import domains.development.Commit


data class RepoData(
    val repoName: String,
    val commits: List<Commit>
)