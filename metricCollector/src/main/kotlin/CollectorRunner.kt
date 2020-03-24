import collectors.RepoCollector
import domains.metric.repo.RepoMetricComponent
import domains.metricComponents.CollectorType
import fetchers.GithubRepoFetcher

class CollectorRunner {

    private val metricServices = di.ServiceLocator.metricService

    fun fetchAndRunCollectors() {
        val metrics = metricServices.getNonSyncedMetrics(5 * 60 * 60)

        metrics.forEach {
            when (it.metricCollectorData.collectorType) {
                CollectorType.GITHUB_REPO -> {
                    RepoCollector(GithubRepoFetcher(), it as RepoMetricComponent).collectData()
                }
            }
        }
    }
}