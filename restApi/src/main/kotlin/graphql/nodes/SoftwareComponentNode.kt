package graphql.nodes

import domains.codeManagement.repo.RepoToolType
import domains.project.SoftwareComponent

data class SoftwareComponentNode(
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
    ) = CodeManagementNode.from(
        SoftwareComponent(id, name).setCodeManagement(
            repoToolType,
            repoUrl,
            repoToken
        )
    )

    fun removeCodeManagementTool(): Boolean {
        return SoftwareComponent(id, name).removeCodeManagementTool()
    }

    fun codeManagementTool() = SoftwareComponent(id, name).getCodeManagement()?.let {
        CodeManagementNode.from(it)
    }
}
