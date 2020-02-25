import domains.metric.CollectorType

class CollectorRunner {

    private val metricServices = di.ServiceLocator.metricService

    fun fetchAndRunCollectors() {
        val metrics = metricServices.getNonSyncedMetrics(5 * 60 * 60)

        metrics.forEach {
            when (it.metricCollectorInfo.collectorType) {
                CollectorType.GITHUB_REPO -> {

                }
            }
        }
    }
}