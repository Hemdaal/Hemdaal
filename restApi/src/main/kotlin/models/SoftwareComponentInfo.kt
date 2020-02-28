package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.SoftwareComponent
import domains.metric.CollectorType
import domains.metric.MetricCollectorData
import domains.metric.MetricType
import main.kotlin.graphql.GraphQLCallContext

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(softwareComponent.id, softwareComponent.name)

    fun metrics(@GraphQLContext context: GraphQLCallContext): MetricInfos {
        val repoMetricInfos = mutableListOf<RepoMetricInfo>()
        SoftwareComponent(id, name).getMetrics().map {
            when (it.type) {
                MetricType.REPO -> {
                    repoMetricInfos.add(RepoMetricInfo(it))
                }
            }
        }

        return MetricInfos(
            repoMetricInfos = repoMetricInfos
        )
    }

    fun addMetric(
        @GraphQLContext context: GraphQLCallContext,
        metricCollectorInfo: MetricCollectorInfo,
        metricType: MetricType
    ): MetricInfos {

        val repoMetricInfos = mutableListOf<RepoMetricInfo>()

        val metric = SoftwareComponent(id, name).addMetricComponent(
            metricType, MetricCollectorData(
                resourceUrl = metricCollectorInfo.resourceUrl,
                token = metricCollectorInfo.token,
                collectorType = CollectorType.valueOf(metricCollectorInfo.collectorType)
            )
        )

        when (metric.type) {
            MetricType.REPO -> {
                repoMetricInfos.add(RepoMetricInfo(metric))
            }
        }

        return MetricInfos(
            repoMetricInfos = repoMetricInfos
        )
    }
}