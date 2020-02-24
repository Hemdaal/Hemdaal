package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.Organisation
import main.kotlin.graphql.GraphQLCallContext

data class OrganisationInfo(
    val id: Long,
    val name: String
) {
    constructor(organisation: Organisation) : this(organisation.id, organisation.name)

    fun projects(@GraphQLContext context: GraphQLCallContext): List<ProjectInfo> {
        return Organisation(id, name).getProjects().map {
            ProjectInfo(it)
        }
    }

    fun collaborators(@GraphQLContext context: GraphQLCallContext): List<CollaboratorInfo> {
        return Organisation(id, name).getCollaborators().map {
            CollaboratorInfo(it.key)
        }
    }

    fun addCollaborator(
        @GraphQLContext context: GraphQLCallContext,
        userName: String,
        userEmail: String
    ): CollaboratorInfo {
        return Organisation(id, name).addCollaborator(userName, userEmail).let {
            CollaboratorInfo(it)
        }
    }
}