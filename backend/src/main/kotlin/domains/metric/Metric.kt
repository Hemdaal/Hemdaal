package domains.metric

abstract class Metric(
    val id: Long,
    val type: MetricType,
    val metricCollectorInfo: MetricCollectorInfo,
    var lastSynced: Long
)