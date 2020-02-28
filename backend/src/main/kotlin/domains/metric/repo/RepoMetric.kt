package domains.metric.repo

import di.ServiceLocator
import domains.metric.Metric
import domains.metric.MetricCollectorData
import domains.metric.MetricType
import repositories.CommitRepository
import repositories.MetricRepository

class RepoMetric(
    id: Long,
    metricCollectorData: MetricCollectorData,
    lastSyncedCache: Long = 0
) : Metric(
    id,
    MetricType.REPO,
    metricCollectorData,
    lastSyncedCache
) {

    private val metricRepository: MetricRepository = ServiceLocator.metricRepository
    private val commitRepository: CommitRepository = ServiceLocator.commitRepository

    fun addCommits(commits: List<Commit>) {
        commitRepository.addCommits(id, commits)
    }

    fun getCommits(): List<Commit> {
        return commitRepository.getCommitsByMetricId(id)
    }

    override fun getLastSynced(): Long {
        return metricRepository.getLastSync(id)
    }
}