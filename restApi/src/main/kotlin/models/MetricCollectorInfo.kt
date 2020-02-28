package main.kotlin.models

import domains.metric.MetricCollectorData

data class MetricCollectorInfo(
    val resourceUrl: String,
    val token: String,
    val collectorType: String
) {
    constructor(metricCollectorData: MetricCollectorData) : this(
        metricCollectorData.resourceUrl,
        metricCollectorData.token,
        metricCollectorData.collectorType.name
    )
}