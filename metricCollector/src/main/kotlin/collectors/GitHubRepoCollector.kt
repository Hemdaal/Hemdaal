package collectors

import domains.metric.repo.RepoMetric

class GitHubRepoCollector(private val repoMetric: RepoMetric) : RepoCollector {

    override fun collectData() {

        //TODO hit repo api
    }
}