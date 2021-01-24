package domains.development.repo

import domains.development.Commit
import domains.development.RepoTool
import gitlab.GitlabCommitCollector
import gitlab.GitlabProjectUrl

class GitLabRepoTool(
    url: String,
    softwareId: Long,
    val token: String?
) : RepoTool(url, softwareId) {

    override fun collect() {
        val lastCommitTime = getLastCommitTime()
        collectCommitAndAdd(0, lastCommitTime)

        //TODO collect MRS
    }

    private fun collectCommitAndAdd(page: Int, since: Long) {
        val pageCount = 10

        val gitlabCommits = GitlabCommitCollector(GitlabProjectUrl(repoUrl), token).getCommits(page, pageCount, since)
        if (gitlabCommits.isNotEmpty()) {
            saveCommits(gitlabCommits.map {
                Commit(
                    id = 0L,
                    sha = it.sha,
                    message = it.message,
                    authorId = getAuthorId(it.authorName, it.authorEmail),
                    time = it.getCommitTime()
                )
            })
            collectCommitAndAdd(page + 1, since)
        }
    }
}
