package repositories

import db.ProjectManagementTable
import db.SoftwareComponentTable
import domains.projectManagement.PMTool
import domains.projectManagement.PMToolType
import domains.projectManagement.ProjectManagement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class ProjectManagementRepository {

    fun addProjectManagement(
        projectId: Long,
        url: String,
        type: PMToolType,
        token: String?
    ) = ProjectManagementTable.insert {
        it[ProjectManagementTable.project_id] = projectId
        it[ProjectManagementTable.url] = url
        it[ProjectManagementTable.token] = token
        it[ProjectManagementTable.type] = type.name
    }.let {
        convertRowToProjectManagement(it)
    }


    fun getProjectManagement(projectId: Long) =
        ProjectManagementTable.select(where = { ProjectManagementTable.project_id eq projectId }).singleOrNull()
            ?.let {
                convertRowToProjectManagement(it)
            }

    fun getProjectManagementFromSoftwareId(softwareId: Long) =
        ProjectManagementTable.leftJoin(SoftwareComponentTable)
            .select(where = { ProjectManagementTable.project_id eq SoftwareComponentTable.projectId })
            .singleOrNull()?.let {
                convertRowToProjectManagement(it)
            }

    private fun convertRowToProjectManagement(row: ResultRow) = ProjectManagement(
        row[ProjectManagementTable.id],
        PMTool(
            row[ProjectManagementTable.url],
            PMToolType.valueOf(row[ProjectManagementTable.type]),
            row[ProjectManagementTable.token]
        )
    )

    private fun convertRowToProjectManagement(row: InsertStatement<Number>) = ProjectManagement(
        row[ProjectManagementTable.id],
        PMTool(
            row[ProjectManagementTable.url],
            PMToolType.valueOf(row[ProjectManagementTable.type]),
            row[ProjectManagementTable.token]
        )
    )
}