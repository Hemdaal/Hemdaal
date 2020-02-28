package domains.metric

abstract class Metric(
    val id: Long,
    val type: MetricType,
    val metricCollectorData: MetricCollectorData,
    val lastSyncCached: Long = 0
) {
    abstract fun getLastSynced(): Long
}