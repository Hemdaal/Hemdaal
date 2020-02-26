package repositories

import db.ProjectTable
import domains.Project
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectRepository {

    fun getProjectsBy(projectIds: List<Long>): List<Project> {
        return transaction {
            ProjectTable.select(where = { ProjectTable.id inList projectIds }).mapNotNull {
                createProjectFrom(it)
            }
        }
    }

    fun createProject(name: String): Project {
        return transaction {
            ProjectTable.insert {
                it[ProjectTable.name] = name
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