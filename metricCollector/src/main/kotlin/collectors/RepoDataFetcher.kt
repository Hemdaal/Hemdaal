package collectors

import domains.metricComponents.MetricCollectorData
import models.CommitData

interface RepoDataFetcher {

    fun fetchCommits(metricCollectorData: MetricCollectorData): List<CommitData>

}