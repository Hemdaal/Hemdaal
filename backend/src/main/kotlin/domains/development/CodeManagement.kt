package domains.development

import di.ServiceLocator
import repositories.CodeManagementRepository

class CodeManagement(
    val id: Long,
    val tool: RepoTool,
    private var lastSynced: Long
) {
    private val codeManagementRepository: CodeManagementRepository = ServiceLocator.codeManagementRepository

    fun getCommits(
        startTime: Long? = 0
    ): List<Commit> {
        return tool.getCommits()
    }

    fun getMergeRequests(
        startTime: Long? = 0
    ): List<MergeRequest> {
        return tool.getMRs()
    }

    fun sync() {
        tool.collect()
        setLastSynced(System.currentTimeMillis())
    }

    fun getLastSynced(): Long {
        return lastSynced
    }

    private fun setLastSynced(lastSynced: Long) {
        this.lastSynced = lastSynced
        codeManagementRepository.setLastSynced(id, lastSynced)
    }
}
