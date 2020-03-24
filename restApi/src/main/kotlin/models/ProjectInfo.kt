package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.project.Project
import domains.user.Access
import main.kotlin.graphql.GraphQLCallContext

data class ProjectInfo(
    val id: Long,
    val name: String,
    val access: AccessInfo
) {
    constructor(project: Project, access: Access) : this(project.id, project.name, AccessInfo(access))

    fun collaborators(@GraphQLContext context: GraphQLCallContext): List<CollaboratorInfo> {
        return Project(id, name).getCollaborators().map {
            CollaboratorInfo(it.key)
        }
    }

    fun softwareComponents(@GraphQLContext context: GraphQLCallContext): List<SoftwareComponentInfo> {
        return Project(id, name).getSoftwareComponents().map {
            SoftwareComponentInfo(it)
        }
    }

    fun softwareComponent(@GraphQLContext context: GraphQLCallContext, softwareId: Long): SoftwareComponentInfo? {
        return Project(id, name).getSoftwareComponent(softwareId)?.let {
            SoftwareComponentInfo(it)
        }
    }

    fun createSoftwareComponent(
        @GraphQLContext context: GraphQLCallContext,
        name: String
    ): SoftwareComponentInfo {
        val softwareComponent = Project(id, name).createSoftwareComponent(name)
        return SoftwareComponentInfo(softwareComponent)
    }

    fun addCollaborator(
        @GraphQLContext context: GraphQLCallContext,
        userName: String,
        userEmail: String
    ): CollaboratorInfo {
        return CollaboratorInfo(Project(id, name).addCollaborator(userName, userEmail))
    }
}