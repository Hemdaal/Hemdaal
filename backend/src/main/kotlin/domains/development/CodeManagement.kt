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
        return emptyList()
    }

    fun getMergeRequests(
        startTime: Long? = 0
    ): List<MergeRequest> {
        return emptyList()
    }

    fun getLastSynced(): Long {
        return lastSynced
    }

    fun setLastSynced(lastSynced: Long) {
        codeManagementRepository.setLastSynced(id, lastSynced)
    }
}
