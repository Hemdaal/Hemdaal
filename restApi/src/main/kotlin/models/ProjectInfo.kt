package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.Project
import domains.Scope
import main.kotlin.graphql.GraphQLCallContext

data class ProjectInfo(
    val id: Long,
    val name: String,
    val scopes: List<Scope>
) {
    constructor(project: Project, scopes: List<Scope>) : this(project.id, project.name, scopes)

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

    fun addCollaborator(
        @GraphQLContext context: GraphQLCallContext,
        userName: String,
        userEmail: String
    ): CollaboratorInfo {
        return Project(id, name).addCollaborator(userName, userEmail).let {
            CollaboratorInfo(it)
        }
    }
}