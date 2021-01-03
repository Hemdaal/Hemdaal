package repositories

import db.SoftwareComponentTable
import domains.project.SoftwareComponent
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class SoftwareComponentRepository {

    fun getSoftwareComponentsBy(projectId: Long): List<SoftwareComponent> {
        return transaction {
            SoftwareComponentTable.select(where = { SoftwareComponentTable.projectId eq projectId }).mapNotNull {
                convertRowToSofwareComponent(projectId, it)
            }
        }
    }

    fun getSoftwareComponentsBy(projectId: Long, softwareId: Long): SoftwareComponent? {
        return transaction {
            SoftwareComponentTable.select(where = { (SoftwareComponentTable.projectId eq projectId) and (SoftwareComponentTable.id eq softwareId) })
                .singleOrNull()?.let {
                    convertRowToSofwareComponent(projectId, it)
            }
        }
    }

    fun createSoftwareComponent(name: String, projectId: Long): SoftwareComponent {
        return transaction {
            SoftwareComponentTable.insert {
                it[SoftwareComponentTable.name] = name
                it[SoftwareComponentTable.projectId] = projectId
            }.let {
                convertRowToSofwareComponent(projectId, it)
            }
        }
    }

    private fun convertRowToSofwareComponent(projectId: Long, row: InsertStatement<Number>): SoftwareComponent {
        return SoftwareComponent(
            id = row[SoftwareComponentTable.id],
            projectId = projectId,
            name = row[SoftwareComponentTable.name]
        )
    }

    private fun convertRowToSofwareComponent(projectId: Long, row: ResultRow): SoftwareComponent {
        return SoftwareComponent(
            id = row[SoftwareComponentTable.id],
            projectId = projectId,
            name = row[SoftwareComponentTable.name]
        )
    }


}
