package services

import repositories.MetricRepository

class CollectorService {

    fun getCollectorsToRun() {
        MetricRepository().getMetrics()
    }
}