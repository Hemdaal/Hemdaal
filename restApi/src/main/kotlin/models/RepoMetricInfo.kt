package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.metric.CollectorType
import domains.metric.Metric
import domains.metric.MetricCollectorData
import domains.metric.repo.RepoMetric
import main.kotlin.graphql.GraphQLCallContext

data class RepoMetricInfo(
    val id: Long,
    val metricCollectorInfo: MetricCollectorInfo,
    val lastSyncCached: Long
) {
    constructor(metric: Metric) : this(
        metric.id,
        MetricCollectorInfo(metric.metricCollectorData),
        metric.lastSyncCached
    )

    fun getCommits(@GraphQLContext context: GraphQLCallContext): List<CommitInfo> {
        return RepoMetric(
            id, MetricCollectorData(
                metricCollectorInfo.resourceUrl,
                metricCollectorInfo.token,
                CollectorType.valueOf(metricCollectorInfo.collectorType)
            )
        ).getCommits().map {
            CommitInfo(it)
        }
    }
}