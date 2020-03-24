package repositories

import db.PMToolTable
import domains.projectManagement.PMTool
import domains.projectManagement.PMToolType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class PMToolRepository {

    fun addPMTool(
        projectId: Long,
        url: String,
        type: PMToolType,
        token: String?
    ): PMTool {

        return PMToolTable.insert {
            it[PMToolTable.project_id] = projectId
            it[PMToolTable.url] = url
            it[PMToolTable.token] = token
            it[PMToolTable.type] = type.name
        }.let {
            convertRowToPMTool(it)
        }
    }

    fun getPMTool(projectId: Long): PMTool? {

        return PMToolTable.select(where = { PMToolTable.project_id eq projectId }).singleOrNull()?.let {
            convertRowToPMTool(it)
        }
    }

    private fun convertRowToPMTool(row: ResultRow): PMTool {
        return PMTool(
            row[PMToolTable.id],
            row[PMToolTable.url],
            PMToolType.valueOf(row[PMToolTable.type]),
            row[PMToolTable.token]
        )
    }

    private fun convertRowToPMTool(row: InsertStatement<Number>): PMTool {
        return PMTool(
            row[PMToolTable.id],
            row[PMToolTable.url],
            PMToolType.valueOf(row[PMToolTable.type]),
            row[PMToolTable.token]
        )
    }
}