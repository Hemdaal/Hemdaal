package repositories

import db.ProjectWidgetTable
import domains.dashboard.widgets.CommitByDayWidget
import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType
import domains.dashboard.widgets.ProjectWidgetType.COMMIT_BY_DAY
import domains.dashboard.widgets.ProjectWidgetType.valueOf
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectWidgetRepository {

    fun getProjectWidgets(widgetIds: List<Long>, userId: Long): List<ProjectWidget> {
        return transaction {
            ProjectWidgetTable.select(where = {
                ProjectWidgetTable.id.inList(widgetIds).and(ProjectWidgetTable.userId.eq(userId))
            }).map {
                convertRowToProjectWidget(it)
            }
        }
    }

    fun getProjectWidget(widgetId: Long, userId: Long): ProjectWidget? {
        return transaction {
            ProjectWidgetTable.select(where = {
                ProjectWidgetTable.id.eq(widgetId).and(ProjectWidgetTable.userId.eq(userId))
            }).firstOrNull()?.let {
                convertRowToProjectWidget(it)
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
                convertRowToProjectWidget(it)
            }
        }
    }


    fun removeWidget(id: Long, userId: Long) {
        transaction {
            ProjectWidgetTable.deleteWhere { ProjectWidgetTable.id.eq(id).and(ProjectWidgetTable.userId.eq(userId)) }
        }
    }

    private fun convertRowToProjectWidget(row: ResultRow): ProjectWidget {

        val id = row[ProjectWidgetTable.id]
        val name = row[ProjectWidgetTable.name]
        val type = valueOf(row[ProjectWidgetTable.type])
        val projectId = row[ProjectWidgetTable.projectId]
        val additionalInfo = row[ProjectWidgetTable.additionalInfo] ?: "0"

        return when (type) {
            COMMIT_BY_DAY -> CommitByDayWidget(id, name, projectId, additionalInfo)
        }
    }

    private fun convertRowToProjectWidget(row: InsertStatement<Number>): ProjectWidget {

        val id = row[ProjectWidgetTable.id]
        val name = row[ProjectWidgetTable.name]
        val type = valueOf(row[ProjectWidgetTable.type])
        val projectId = row[ProjectWidgetTable.projectId]
        val additionalInfo = row[ProjectWidgetTable.additionalInfo] ?: "0"

        return when (type) {
            COMMIT_BY_DAY -> CommitByDayWidget(id, name, projectId, additionalInfo)
        }
    }
}
