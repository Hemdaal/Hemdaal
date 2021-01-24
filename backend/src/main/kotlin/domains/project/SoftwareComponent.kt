package domains.project

import di.ServiceLocator
import domains.development.RepoToolType

class SoftwareComponent(
    val id: Long,
    val name: String
) {

    private val codeManagementRepository = ServiceLocator.codeManagementRepository

    fun setCodeManagement(
        repoToolType: RepoToolType,
        repoUrl: String,
        token: String?
    ) = codeManagementRepository.addCodeManagement(
        id,
        repoUrl,
        repoToolType,
        token
    )

    fun getCodeManagement() = codeManagementRepository.getCodeManagement(id)

    fun syncMetrics() {
        getCodeManagement()?.sync()
    }
}
