package collectors

import domains.metric.MetricCollectorData
import models.CommitData

interface RepoDataFetcher {

    fun fetchCommits(metricCollectorData: MetricCollectorData): List<CommitData>

}