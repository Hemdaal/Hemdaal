package main.kotlin.models

import domains.development.GITToolType
import domains.project.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(softwareComponent.id, softwareComponent.name)

    fun setToolForCodeManagement(
        gitToolType: GITToolType,
        gitRepoUrl: String,
        gitRepoToken: String?
    ) = CodeManagementInfo(SoftwareComponent(id, name).setCodeManagement(gitToolType, gitRepoUrl, gitRepoToken))

    fun getToolForCodeManagement() = SoftwareComponent(id, name).getCodeManagement()?.let {
        CodeManagementInfo(it)
    }
}