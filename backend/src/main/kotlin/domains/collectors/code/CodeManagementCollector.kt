package domains.collectors.code

import di.ServiceLocator
import domains.codeManagement.CodeManagement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repositories.CodeManagementRepository

class CodeManagementCollector {

    private val codeManagementRepository: CodeManagementRepository = ServiceLocator.codeManagementRepository

    fun collect() {
        val syncTime = System.currentTimeMillis()
        val lastSyncedRepoTools = getNonSyncedRepoTools(syncTime)
        syncCodeManagements(lastSyncedRepoTools)
    }

    private fun syncCodeManagements(repoTools: List<CodeManagement>) {
        repoTools.forEach { codeManagement ->
            CoroutineScope(Dispatchers.Default).launch {
                codeManagement.sync()
            }
        }
    }

    private fun getNonSyncedRepoTools(syncTime: Long): List<CodeManagement> {
        return codeManagementRepository.getLastSyncedCodeManagements(syncTime)
    }
}
