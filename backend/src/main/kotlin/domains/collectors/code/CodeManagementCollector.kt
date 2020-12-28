package domains.collectors.code

import domains.development.RepoTool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodeManagementCollector {

    fun collect() {
        val syncTime = System.currentTimeMillis()
        val lastSyncedCodeManagements = getNonSyncedCodeManagements()

        lastSyncedCodeManagements.forEach { codeManagement ->

            CoroutineScope(Dispatchers.Main).launch {
                codeManagement.collect()
            }
        }
    }

    private fun getNonSyncedCodeManagements(): List<RepoTool> {
        return emptyList()
    }
}
