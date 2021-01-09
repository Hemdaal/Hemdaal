package repositories

import db.ProjectWidgetTable
import domains.widgets.CommitWidget
import domains.widgets.ProjectWidget
import domains.widgets.ProjectWidgetType
import domains.widgets.ProjectWidgetType.COMMIT
import domains.widgets.ProjectWidgetType.valueOf
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectWidgetRepository {

    fun getProjectWidgets(widgetIds: List<Long>): List<ProjectWidget> {
        return transaction {
            ProjectWidgetTable.select(where = { ProjectWidgetTable.id.inList(widgetIds) }).map {
                convertRowToUserProjectDashboard(it)
            }
        }
    }

    fun addProjectWidget(projectId: Long, userId: Long, type: ProjectWidgetType, additonalInfo: String): ProjectWidget {
        return transaction {
            ProjectWidgetTable.insert {
                it[ProjectWidgetTable.type] = type.name
                it[ProjectWidgetTable.userId] = userId
                it[ProjectWidgetTable.projectId] = projectId
                it[ProjectWidgetTable.additionalInfo] = additonalInfo
            }.let {
                convertRowToUserProjectDashboard(it)
            }
        }
    }


    fun removeWidget(id: Long, userId: Long) {
        transaction {
            ProjectWidgetTable.deleteWhere { ProjectWidgetTable.id.eq(id).and(ProjectWidgetTable.userId.eq(userId)) }
        }
    }

    private fun convertRowToUserProjectDashboard(row: ResultRow): ProjectWidget {

        val id = row[ProjectWidgetTable.id]
        val type = valueOf(row[ProjectWidgetTable.type])
        val projectId = row[ProjectWidgetTable.projectId]
        val additionalInfo = row[ProjectWidgetTable.additionalInfo] ?: "0"

        return when (type) {
            COMMIT -> CommitWidget(id, projectId, additionalInfo)
        }
    }

    private fun convertRowToUserProjectDashboard(row: InsertStatement<Number>): ProjectWidget {

        val id = row[ProjectWidgetTable.id]
        val type = valueOf(row[ProjectWidgetTable.type])
        val projectId = row[ProjectWidgetTable.projectId]
        val additionalInfo = row[ProjectWidgetTable.additionalInfo] ?: "0"

        return when (type) {
            COMMIT -> CommitWidget(id, projectId, additionalInfo)
        }
    }
}
