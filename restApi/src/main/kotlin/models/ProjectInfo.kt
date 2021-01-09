package main.kotlin.models

import domains.project.Project
import domains.user.Access

data class ProjectInfo(
    val id: Long,
    val name: String,
    val access: AccessInfo
) {
    constructor(project: Project, access: Access) : this(project.id, project.name, AccessInfo(access))

    fun collaborators(): List<CollaboratorInfo> {
        return Project(id, name).getCollaborators().map {
            CollaboratorInfo(it.key)
        }
    }

    fun softwareComponents(): List<SoftwareComponentInfo> {
        return Project(id, name).getSoftwareComponents().map {
            SoftwareComponentInfo(it)
        }
    }

    fun softwareComponent(softwareId: Long): SoftwareComponentInfo? {
        return Project(id, name).getSoftwareComponent(softwareId)?.let {
            SoftwareComponentInfo(it)
        }
    }

    fun addSoftwareComponent(
        name: String
    ): SoftwareComponentInfo {
        val softwareComponent = Project(id, name).createSoftwareComponent(name)
        return SoftwareComponentInfo(softwareComponent)
    }

    fun addCollaborator(
        userName: String,
        userEmail: String
    ): CollaboratorInfo {
        return CollaboratorInfo(Project(id, name).addCollaborator(userName, userEmail))
    }
}
