package main.kotlin.models

import domains.development.RepoToolType
import domains.project.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(
        softwareComponent.id,
        softwareComponent.name
    )

    fun setCodeManagementTool(
        repoToolType: RepoToolType,
        repoUrl: String,
        repoToken: String?
    ) = CodeManagementInfo.from(
        SoftwareComponent(id, name).setCodeManagement(
            repoToolType,
            repoUrl,
            repoToken
        )
    )

    fun getCodeManagementTool() = SoftwareComponent(id, name).getCodeManagement()?.let {
        CodeManagementInfo.from(it)
    }
}
