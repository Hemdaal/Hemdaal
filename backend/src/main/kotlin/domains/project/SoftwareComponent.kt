package domains.project

import di.ServiceLocator
import domains.build.BuildToolType
import domains.development.GITToolType
import domains.widget.WidgetManagement

class SoftwareComponent(
    val id: Long,
    val name: String
) {

    private val codeManagementRepository = ServiceLocator.codeManagementRepository
    private val buildManagementRepository = ServiceLocator.buildManagementRepository
    private val projectManagementRepository = ServiceLocator.projectManagementRepository

    fun setCodeManagement(
        gitToolType: GITToolType,
        repoUrl: String,
        token: String?
    ) = codeManagementRepository.addCodeManagement(
        id,
        repoUrl,
        gitToolType,
        token
    )

    fun getCodeManagement() = codeManagementRepository.getCodeManagement(id)

    fun setBuildManagement(
        buildToolType: BuildToolType,
        repoUrl: String,
        token: String?
    ) = buildManagementRepository.addBuildManagement(
        id,
        repoUrl,
        buildToolType,
        token
    )

    fun getBuildManagement() = buildManagementRepository.getBuildManagement(id)

    fun getProjectManagement() = projectManagementRepository.getProjectManagementFromSoftwareId(id)

    fun getWidgetManagement(): WidgetManagement {
        return WidgetManagement(
            projectManagement = getProjectManagement(),
            codeManagement = getCodeManagement(),
            buildManagement = getBuildManagement()
        )
    }
}