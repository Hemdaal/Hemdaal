package domains

import di.ServiceLocator
import domains.metric.Metric
import domains.metric.MetricCollectorData
import domains.metric.MetricType
import repositories.MetricRepository

class SoftwareComponent(
    val id: Long,
    val name: String
) {

    private val metricRepository: MetricRepository = ServiceLocator.metricRepository

    fun addMetricComponent(metricType: MetricType, metricCollectorData: MetricCollectorData): Metric {
        return metricRepository.addMetric(metricType, metricCollectorData, id)
    }

    fun getMetrics(): List<Metric> {
        return metricRepository.getMetrics(id)
    }
}