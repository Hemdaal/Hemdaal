package collectors

import di.ServiceLocator
import domains.metric.repo.Commit
import domains.metric.repo.RepoMetricComponent
import services.CollaboratorService

class RepoCollector(private val repoDataFetcher: RepoDataFetcher, private val repoMetric: RepoMetricComponent) {

    private val collaboratorService: CollaboratorService = ServiceLocator.collaboratorService

    fun collectData() {
        val commitDatas = repoDataFetcher.fetchCommits(repoMetric.metricCollectorData)
        val emailCollaboratorMap = collaboratorService.addCollaborators(commitDatas.associate {
            it.authorName to it.authorEmail
        })
        repoMetric.addCommits(commitDatas.map {
            Commit(
                sha = it.sha,
                authorId = emailCollaboratorMap[it.authorEmail]?.id ?: 0,
                time = it.time
            )
        })
    }
}