package fetchers

import collectors.RepoDataFetcher
import domains.metric.MetricCollectorData
import domains.metric.repo.Commit

class GithubRepoFetcher : RepoDataFetcher {

    override fun fetchCommits(metricCollectorData: MetricCollectorData): List<Commit> {
        //TODO
        return emptyList()
    }
}