package repositories

import db.ProjectCollaboratorTable
import db.SoftwareComponentTable
import domains.project.SoftwareComponent
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class SoftwareComponentRepository {

    fun getSoftwareComponentsByProject(projectId: Long): List<SoftwareComponent> {
        return transaction {
            SoftwareComponentTable.select(where = { SoftwareComponentTable.projectId eq projectId }).mapNotNull {
                convertRowToSofwareComponent(it)
            }
        }
    }

    fun getSoftwareComponentsByProject(projectId: Long, softwareId: Long): SoftwareComponent? {
        return transaction {
            SoftwareComponentTable.select(where = { (SoftwareComponentTable.projectId eq projectId) and (SoftwareComponentTable.id eq softwareId) })
                .singleOrNull()?.let {
                    convertRowToSofwareComponent(it)
                }
        }
    }

    fun getSoftwareComponentsByUser(userId: Long, softwareId: Long): SoftwareComponent? {
        return transaction {
            SoftwareComponentTable.join(
                ProjectCollaboratorTable,
                JoinType.INNER,
                additionalConstraint = {
                    ProjectCollaboratorTable.projectId.eq(SoftwareComponentTable.projectId)
                        .and(ProjectCollaboratorTable.collaboratorId.eq(userId))
                })
                .select(where = { SoftwareComponentTable.id eq softwareId })
                .singleOrNull()?.let {
                    convertRowToSofwareComponent(it)
                }
        }
    }

    fun createSoftwareComponent(name: String, projectId: Long): SoftwareComponent {
        return transaction {
            SoftwareComponentTable.insert {
                it[SoftwareComponentTable.name] = name
                it[SoftwareComponentTable.projectId] = projectId
            }.let {
                convertRowToSofwareComponent(it)
            }
        }
    }

    private fun convertRowToSofwareComponent(row: InsertStatement<Number>): SoftwareComponent {
        return SoftwareComponent(
            id = row[SoftwareComponentTable.id],
            name = row[SoftwareComponentTable.name]
        )
    }

    private fun convertRowToSofwareComponent(row: ResultRow): SoftwareComponent {
        return SoftwareComponent(
            id = row[SoftwareComponentTable.id],
            name = row[SoftwareComponentTable.name]
        )
    }


}
