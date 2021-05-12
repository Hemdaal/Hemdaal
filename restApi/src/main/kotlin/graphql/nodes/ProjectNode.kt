package graphql.nodes

import domains.project.Project
import domains.user.Access
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ProjectNode(
    val id: Long,
    val name: String,
    val access: AccessNode
) {
    constructor(project: Project, access: Access) : this(project.id, project.name, AccessNode(access))

    fun collaborators(): List<CollaboratorNode> {
        return Project(id, name).getCollaborators().map {
            CollaboratorNode(it.key)
        }
    }

    fun softwareComponents(): List<SoftwareComponentNode> {
        return Project(id, name).getSoftwareComponents().map {
            SoftwareComponentNode(it)
        }
    }

    fun softwareComponent(softwareId: Long): SoftwareComponentNode? {
        return Project(id, name).getSoftwareComponent(softwareId)?.let {
            SoftwareComponentNode(it)
        }
    }

    fun addSoftwareComponent(
        name: String
    ): SoftwareComponentNode {
        val softwareComponent = Project(id, name).createSoftwareComponent(name)
        return SoftwareComponentNode(softwareComponent)
    }

    fun sync(): Boolean {
        CoroutineScope(Dispatchers.Default).launch {
            Project(id, name).syncMetrics()
        }
        return true
    }
}
