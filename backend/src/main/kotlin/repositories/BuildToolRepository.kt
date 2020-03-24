package repositories

import db.BuildToolTable
import domains.build.BuildTool
import domains.build.BuildToolType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class BuildToolRepository {

    fun addBuildTool(
        softwareId: Long,
        url: String,
        type: BuildToolType,
        token: String?
    ): BuildTool {

        return BuildToolTable.insert {
            it[BuildToolTable.softwareId] = softwareId
            it[BuildToolTable.url] = url
            it[BuildToolTable.token] = token
            it[BuildToolTable.type] = type.name
        }.let {
            convertRowToPMTool(it)
        }
    }

    fun getBuildTool(softwareId: Long): BuildTool? {

        return BuildToolTable.select(where = { BuildToolTable.softwareId eq softwareId }).singleOrNull()?.let {
            convertRowToPMTool(it)
        }
    }

    private fun convertRowToPMTool(row: ResultRow): BuildTool {
        return BuildTool(
            row[BuildToolTable.id],
            row[BuildToolTable.url],
            BuildToolType.valueOf(row[BuildToolTable.type]),
            row[BuildToolTable.token]
        )
    }

    private fun convertRowToPMTool(row: InsertStatement<Number>): BuildTool {
        return BuildTool(
            row[BuildToolTable.id],
            row[BuildToolTable.url],
            BuildToolType.valueOf(row[BuildToolTable.type]),
            row[BuildToolTable.token]
        )
    }
}