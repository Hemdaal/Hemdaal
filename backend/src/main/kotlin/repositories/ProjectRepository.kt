package repositories

import db.ProjectTable
import domains.Project
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

class ProjectRepository {

    fun getOrganisationProjects(orgId: Long): List<Project>? {
        return ProjectTable.select(where = { ProjectTable.organisationId eq orgId }).mapNotNull {
            createProjectFrom(it)
        }
    }

    private fun createProjectFrom(row: ResultRow): Project {
        return Project(
            id = row[ProjectTable.id],
            name = row[ProjectTable.name]
        )
    }
}