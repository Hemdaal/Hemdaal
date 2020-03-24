package repositories

import db.CodeManagementTable
import domains.development.CodeManagement
import domains.development.GITTool
import domains.development.GITToolType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class CodeManagementRepository {

    fun addCodeManagement(
        softwareId: Long,
        url: String,
        type: GITToolType,
        token: String?
    ) = CodeManagementTable.insert {
        it[CodeManagementTable.softwareId] = softwareId
        it[CodeManagementTable.url] = url
        it[CodeManagementTable.token] = token
        it[CodeManagementTable.type] = type.name
    }.let {
        convertRowToCodeManagement(it)
    }

    fun getCodeManagement(softwareId: Long) =
        CodeManagementTable.select(where = { CodeManagementTable.softwareId eq softwareId }).singleOrNull()?.let {
            convertRowToCodeManagement(it)
        }


    private fun convertRowToCodeManagement(row: ResultRow) = CodeManagement(
        row[CodeManagementTable.id],
        GITTool(
            GITToolType.valueOf(row[CodeManagementTable.type]),
            row[CodeManagementTable.url],
            row[CodeManagementTable.token]
        )
    )

    private fun convertRowToCodeManagement(row: InsertStatement<Number>) = CodeManagement(
        row[CodeManagementTable.id],
        GITTool(
            GITToolType.valueOf(row[CodeManagementTable.type]),
            row[CodeManagementTable.url],
            row[CodeManagementTable.token]
        )
    )
}