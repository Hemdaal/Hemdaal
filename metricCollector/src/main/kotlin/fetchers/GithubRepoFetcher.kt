package fetchers

import collectors.RepoDataFetcher
import domains.metricComponents.MetricCollectorData
import io.ktor.client.request.get
import models.CommitData
import network.NetworkEngine

class GithubRepoFetcher : RepoDataFetcher {

    override fun fetchCommits(metricCollectorData: MetricCollectorData): List<CommitData> {

        NetworkEngine.client.get(metricCollectorData.resourceUrl)


        return emptyList()
    }
}