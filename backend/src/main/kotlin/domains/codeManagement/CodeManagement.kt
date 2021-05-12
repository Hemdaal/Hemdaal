package domains.codeManagement

import di.ServiceLocator
import domains.codeManagement.repo.RepoTool
import repositories.CodeManagementRepository

class CodeManagement(
    val id: Long,
    val tool: RepoTool,
    private var lastSynced: Long?
) {
    private val codeManagementRepository: CodeManagementRepository = ServiceLocator.codeManagementRepository

    fun sync() {
        tool.collect()
        setLastSynced(System.currentTimeMillis())
    }

    fun getLastSynced(): Long? {
        return lastSynced
    }

    private fun setLastSynced(lastSynced: Long) {
        this.lastSynced = lastSynced
        codeManagementRepository.setLastSynced(id, lastSynced)
    }
}
