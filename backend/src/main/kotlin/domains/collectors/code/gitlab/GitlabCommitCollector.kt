package domains.collectors.code.gitlab

import com.google.gson.reflect.TypeToken
import utils.network.GraphQLRequest
import utils.network.NetworkEngine
import utils.network.RequestBuilder
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class GitlabCommitCollector(private val url: GitlabProjectUrl, private val token: String? = null) {

    fun getCommits(page: Int, count: Int, since: Long): List<GitlabCommitDTO> {

        val projectId = getProjectId() ?: throw IllegalArgumentException("Unable to fetch project id")
        val formattedSince = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date(since))

        val request = RequestBuilder(url.getBaseUrl())
            .appendPath("api/v4/projects/")
            .appendPath(projectId)
            .appendPath("repository/commits")
            .addParams("page", page)
            .addParams("per_page", count)
            .addParams("since", formattedSince)

        token?.let {
            request.addHeader("PRIVATE-TOKEN", token)
        }

        val type: Type = object : TypeToken<List<GitlabCommitDTO>>() {}.type

        return NetworkEngine.execute(request.build(), type)
            ?: throw IllegalArgumentException("Unable to fetch commmits")
    }

    private fun getProjectId(): Int? {
        val request = RequestBuilder(url.getBaseUrl()).appendPath("api/graphql")
        val query =
            GraphQLRequest("query getProjectId(\$projectPath: ID!) {\\n  project(fullPath: \$projectPath) {\\n    id\\n  }\\n}")
                .addVariable("projectPath", url.getProjectPath())
        request.addPayload(query)

        return NetworkEngine.execute(request.build(), GitlabProjectResponse::class.java)?.data?.getProjectId()
    }
}
