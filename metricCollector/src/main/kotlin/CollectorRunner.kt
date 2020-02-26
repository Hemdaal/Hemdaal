import collectors.RepoCollector
import domains.metric.CollectorType
import domains.metric.repo.RepoMetric
import fetchers.GithubRepoFetcher

class CollectorRunner {

    private val metricServices = di.ServiceLocator.metricService

    fun fetchAndRunCollectors() {
        val metrics = metricServices.getNonSyncedMetrics(5 * 60 * 60)

        metrics.forEach {
            when (it.metricCollectorInfo.collectorType) {
                CollectorType.GITHUB_REPO -> {
                    RepoCollector(GithubRepoFetcher(), it as RepoMetric).collectData()
                }
            }
        }
    }
}