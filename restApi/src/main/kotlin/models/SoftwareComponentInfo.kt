package main.kotlin.models

import domains.development.RepoToolType
import domains.project.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val projectId: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(
        softwareComponent.id,
        softwareComponent.projectId,
        softwareComponent.name
    )

    fun setCodeManagementTool(
        repoToolType: RepoToolType,
        repoUrl: String,
        repoToken: String?
    ) = CodeManagementInfo.from(
        SoftwareComponent(id, projectId, name).setCodeManagement(
            repoToolType,
            repoUrl,
            repoToken
        )
    )

    fun getCodeManagementTool() = SoftwareComponent(id, projectId, name).getCodeManagement()?.let {
        CodeManagementInfo.from(it)
    }
}
