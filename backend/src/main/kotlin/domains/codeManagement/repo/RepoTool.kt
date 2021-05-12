package domains.codeManagement.repo

import di.ServiceLocator
import domains.development.Commit
import repositories.CommitRepository
import java.util.concurrent.TimeUnit

abstract class RepoTool(
    val repoUrl: String,
    val softwareId: Long
) {
    private val commitRepository: CommitRepository = ServiceLocator.commitRepository

    abstract fun collect()

    protected fun saveCommits(commits: List<Commit>) {
        commitRepository.addCommits(softwareId, commits)
    }

    protected fun getLastCommitTime(): Long {
        return commitRepository.getLastCommit(softwareId)?.time ?: System.currentTimeMillis() - TimeUnit.DAYS.toMillis(
            14
        )
    }

    protected fun getAuthorId(name: String?, email: String?): Long? {
        return commitRepository.getAuthor(name, email)?.id ?: let {
            if (name != null || email != null) {
                commitRepository.addAuthor(name, email)?.id
            } else {
                null
            }
        }
    }
}
