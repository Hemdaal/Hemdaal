package services

import di.ServiceLocator
import domains.metric.Metric
import repositories.MetricRepository

class MetricService {

    private val metricRepository: MetricRepository = ServiceLocator.metricRepository

    fun getNonSyncedMetrics(bufferTime: Long): List<Metric> {
        return metricRepository.getNonSyncedMetrics(bufferTime)
    }
}