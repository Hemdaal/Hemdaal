package domains.metric.repo

import domains.metric.Metric
import domains.metric.MetricCollectorInfo
import domains.metric.MetricType

class RepoMetric(
    id: Long,
    metricCollectorInfo: MetricCollectorInfo,
    lastSynced: Long
) : Metric(
    id,
    MetricType.REPO, metricCollectorInfo, lastSynced
) {

    fun addCommits(commits: List<Commit>) {
        //TODO
    }

    fun getRepoInfo() {
        //TODO
    }

    fun getCommits() {
        //TODO
    }

    fun getMergeRequests() {
        //TODO
    }
}