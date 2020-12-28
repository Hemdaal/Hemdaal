package main.kotlin.models

import domains.development.RepoToolType
import domains.project.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(softwareComponent.id, softwareComponent.name)

    fun setToolForCodeManagement(
        repoToolType: RepoToolType,
        gitRepoUrl: String,
        gitRepoToken: String?
    ) = CodeManagementInfo.from(SoftwareComponent(id, name).setCodeManagement(repoToolType, gitRepoUrl, gitRepoToken))

    fun getToolForCodeManagement() = SoftwareComponent(id, name).getCodeManagement()?.let {
        CodeManagementInfo.from(it)
    }
}
