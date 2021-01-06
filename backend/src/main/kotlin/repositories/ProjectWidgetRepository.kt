package repositories

import db.ProjectWidgetTable
import domains.widgets.CommitWidget
import domains.widgets.ProjectWidget
import domains.widgets.ProjectWidgetType.COMMIT
import domains.widgets.ProjectWidgetType.valueOf
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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

    fun addProjectWidget(projectId: Long, widget: ProjectWidget): ProjectWidget {
        return transaction {
            ProjectWidgetTable.insert {
                it[ProjectWidgetTable.type] = widget.getType().name
                it[ProjectWidgetTable.projectId] = projectId
                it[ProjectWidgetTable.additionalInfo] = widget.getAdditionalInfo()
            }.let {
                convertRowToUserProjectDashboard(it)
            }
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
