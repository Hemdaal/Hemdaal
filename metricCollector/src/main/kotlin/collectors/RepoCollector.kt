package collectors

import domains.metric.repo.RepoMetric

class RepoCollector(private val repoDataFetcher: RepoDataFetcher, private val repoMetric: RepoMetric) {

    fun collectData() {
        val commits = repoDataFetcher.fetchCommits(repoMetric.metricCollectorInfo)
        repoMetric.addCommits(commits)
    }
}