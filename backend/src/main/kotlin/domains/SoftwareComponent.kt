package domains

import domains.metric.MetricCollectorInfo
import domains.metric.MetricType

class SoftwareComponent(
    val id: Long,
    val name: String
) {

    fun addMetricComponent(metricType: MetricType, metricCollectorInfo: MetricCollectorInfo) {

    }
}