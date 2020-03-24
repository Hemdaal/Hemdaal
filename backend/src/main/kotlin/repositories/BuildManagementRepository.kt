package repositories

import db.BuildManagementTable
import domains.build.BuildManagement
import domains.build.BuildTool
import domains.build.BuildToolType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class BuildManagementRepository {

    fun addBuildManagement(
        softwareId: Long,
        url: String,
        type: BuildToolType,
        token: String?
    ) = BuildManagementTable.insert {
        it[BuildManagementTable.softwareId] = softwareId
        it[BuildManagementTable.url] = url
        it[BuildManagementTable.token] = token
        it[BuildManagementTable.type] = type.name
    }.let {
        convertRowToBuildManagement(it)
    }

    fun getBuildManagement(softwareId: Long) =
        BuildManagementTable.select(where = { BuildManagementTable.softwareId eq softwareId }).singleOrNull()
            ?.let {
                convertRowToBuildManagement(it)
            }


    private fun convertRowToBuildManagement(row: ResultRow) = BuildManagement(
        row[BuildManagementTable.id],
        BuildTool(
            row[BuildManagementTable.url],
            BuildToolType.valueOf(row[BuildManagementTable.type]),
            row[BuildManagementTable.token]
        )
    )

    private fun convertRowToBuildManagement(row: InsertStatement<Number>) = BuildManagement(
        row[BuildManagementTable.id],
        BuildTool(
            row[BuildManagementTable.url],
            BuildToolType.valueOf(row[BuildManagementTable.type]),
            row[BuildManagementTable.token]
        )
    )
}