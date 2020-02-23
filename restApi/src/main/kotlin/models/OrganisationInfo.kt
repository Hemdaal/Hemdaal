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

    fun users(@GraphQLContext context: GraphQLCallContext): List<UserInfo> {
        return Organisation(id, name).getUsers().map {
            UserInfo(it.key)
        }
    }
}