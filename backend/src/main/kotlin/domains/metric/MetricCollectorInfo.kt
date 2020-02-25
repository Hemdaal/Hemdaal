package domains.metric

data class MetricCollectorInfo(
    val resourceUrl: String,
    val token: String,
    val collectorType: CollectorType
)