package repositories

import db.CollaboratorTable
import db.ProjectCollaboratorTable
import db.ProjectTable
import domains.Collaborator
import domains.Project
import domains.Scope
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectCollaboratorRepository {

    fun getCollaboratorAccess(projectId: Long): Map<Collaborator, List<Scope>> {
        return transaction {
            ProjectCollaboratorTable.leftJoin(CollaboratorTable)
                .select(where = { (ProjectCollaboratorTable.projectId eq projectId) and (ProjectCollaboratorTable.collaboratorId eq CollaboratorTable.id) })
                .associate {
                    convertRowToCollaborator(it) to createScopesFrom(it[ProjectCollaboratorTable.scopes])
                }
        }
    }

    fun getProjectsWithScope(colId: Long): Map<Project, List<Scope>> {
        return transaction {
            ProjectCollaboratorTable.leftJoin(ProjectTable)
                .select(where = { (ProjectCollaboratorTable.collaboratorId eq colId) and (ProjectCollaboratorTable.projectId eq ProjectTable.id) })
                .associate {
                    createProjectFrom(it) to createScopesFrom(it[ProjectCollaboratorTable.scopes])
                }
        }
    }

    fun getProjectWithScope(colId: Long, projectId: Long): Pair<Project, List<Scope>>? {
        return transaction {
            ProjectCollaboratorTable.leftJoin(ProjectTable)
                .select(where = { (ProjectCollaboratorTable.collaboratorId eq colId) and (ProjectCollaboratorTable.projectId eq ProjectTable.id) and (ProjectTable.id eq projectId) })
                .singleOrNull()?.let {
                    createProjectFrom(it) to createScopesFrom(it[ProjectCollaboratorTable.scopes])
                }
        }
    }

    fun createCollaboratorAccess(collaboratorId: Long, projectId: Long, scopes: List<Scope>) {
        return transaction {
            ProjectCollaboratorTable.insert {
                it[ProjectCollaboratorTable.projectId] = projectId
                it[ProjectCollaboratorTable.collaboratorId] = collaboratorId
                it[ProjectCollaboratorTable.scopes] = scopes.joinToString(",")
            }
        }
    }

    private fun createScopesFrom(scopeString: String): List<Scope> {
        try {
            val scopes = scopeString.split(",")
            return scopes.map {
                Scope.valueOf(it)
            }
        } catch (ignore: Exception) {
        }

        return emptyList()
    }

    private fun createProjectFrom(row: ResultRow): Project {
        return Project(
            id = row[ProjectTable.id],
            name = row[ProjectTable.name]
        )
    }

    private fun convertRowToCollaborator(row: ResultRow): Collaborator {
        return Collaborator(
            id = row[CollaboratorTable.id],
            name = row[CollaboratorTable.name],
            email = row[CollaboratorTable.email]
        )
    }
}