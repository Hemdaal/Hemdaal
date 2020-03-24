package repositories

import db.GITToolTable
import domains.development.GITTool
import domains.development.GITToolType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class GITToolRepository {

    fun addGITTool(
        softwareId: Long,
        url: String,
        type: GITToolType,
        token: String?
    ): GITTool {

        return GITToolTable.insert {
            it[GITToolTable.softwareId] = softwareId
            it[GITToolTable.url] = url
            it[GITToolTable.token] = token
            it[GITToolTable.type] = type.name
        }.let {
            convertRowToPMTool(it)
        }
    }

    fun getGITTool(softwareId: Long): GITTool? {

        return GITToolTable.select(where = { GITToolTable.softwareId eq softwareId }).singleOrNull()?.let {
            convertRowToPMTool(it)
        }
    }

    private fun convertRowToPMTool(row: ResultRow): GITTool {
        return GITTool(
            row[GITToolTable.id],
            GITToolType.valueOf(row[GITToolTable.type]),
            row[GITToolTable.url],
            row[GITToolTable.token]
        )
    }

    private fun convertRowToPMTool(row: InsertStatement<Number>): GITTool {
        return GITTool(
            row[GITToolTable.id],
            GITToolType.valueOf(row[GITToolTable.type]),
            row[GITToolTable.url],
            row[GITToolTable.token]
        )
    }
}