package domains

import domains.metriccollectors.MetricCollector
import domains.metriccollectors.MetricCollectorType

class SoftwareComponent(
    val name: String
) {

    fun getMetricCollectors(): List<MetricCollector> {
        return emptyList()
    }

    fun addMetricCollector(
        resourceUrl: String,
        authInfo: MetricAuthInfo,
        type: MetricCollectorType
    ) {

    }
}