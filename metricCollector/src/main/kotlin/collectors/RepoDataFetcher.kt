package collectors

import domains.metric.MetricCollectorInfo
import domains.metric.repo.Commit

interface RepoDataFetcher {

    fun fetchCommits(metricCollectorInfo: MetricCollectorInfo): List<Commit>

}