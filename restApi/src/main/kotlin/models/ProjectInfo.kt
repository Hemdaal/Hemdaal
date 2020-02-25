package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import domains.Project
import main.kotlin.graphql.GraphQLCallContext

data class ProjectInfo(
    val id: Long,
    val name: String
) {
    constructor(project: Project) : this(project.id, project.name)

    fun softwareComponents(@GraphQLContext context: GraphQLCallContext): List<SoftwareComponentInfo> {
        return Project(id, name).getSoftwareComponents().map {
            SoftwareComponentInfo(it)
        }
    }
}