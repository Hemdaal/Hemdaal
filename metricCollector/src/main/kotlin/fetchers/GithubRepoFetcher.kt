package fetchers

import collectors.RepoDataFetcher
import domains.metric.MetricCollectorInfo
import domains.metric.repo.Commit

class GithubRepoFetcher : RepoDataFetcher {

    override fun fetchCommits(metricCollectorInfo: MetricCollectorInfo): List<Commit> {
        //TODO
        return emptyList()
    }
}