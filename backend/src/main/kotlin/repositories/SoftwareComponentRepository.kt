package repositories

import db.SoftwareComponentTable
import domains.SoftwareComponent
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class SoftwareComponentRepository {

    fun getSoftwareComponentsBy(projectId: Long): List<SoftwareComponent> {
        return SoftwareComponentTable.select(where = { SoftwareComponentTable.projectId eq projectId }).mapNotNull {
            convertRowToSofwareComponent(it)
        }
    }

    fun createSoftwareComponent(name: String, projectId: Long): SoftwareComponent {
        return SoftwareComponentTable.insert {
            it[SoftwareComponentTable.name] = name
            it[SoftwareComponentTable.projectId] = projectId
        }.let {
            convertRowToSofwareComponent(it)
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