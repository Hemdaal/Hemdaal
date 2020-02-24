package repositories

import db.ProjectTable
import domains.Project
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectRepository {

    fun getOrganisationProjects(orgId: Long): List<Project>? {
        return transaction {
            ProjectTable.select(where = { ProjectTable.organisationId eq orgId }).mapNotNull {
                createProjectFrom(it)
            }
        }
    }

    fun createProject(name: String, orgId: Long) {
        return transaction {
            ProjectTable.insert {
                it[ProjectTable.name] = name
                it[ProjectTable.organisationId] = orgId
            }.let {
                createProjectFrom(it)
            }
        }
    }

    private fun createProjectFrom(row: InsertStatement<Number>): Project {
        return Project(
            id = row[ProjectTable.id],
            name = row[ProjectTable.name]
        )
    }

    private fun createProjectFrom(row: ResultRow): Project {
        return Project(
            id = row[ProjectTable.id],
            name = row[ProjectTable.name]
        )
    }
}