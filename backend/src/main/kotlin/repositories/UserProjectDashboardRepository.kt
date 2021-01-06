package repositories

import db.UserProjectDashboardTable
import domains.widgets.UserProjectDashboard
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class UserProjectDashboardRepository {

    fun getUserProjectDashboard(userId: Long, projectId: Long): UserProjectDashboard? {
        return transaction {
            UserProjectDashboardTable.select(where = {
                UserProjectDashboardTable.userId.eq(userId).and(UserProjectDashboardTable.projectId.eq(projectId))
            }).singleOrNull()?.let {
                convertRowToUserProjectDashboard(it)
            }
        }
    }

    fun createUserProjectDashboard(userId: Long, projectId: Long): UserProjectDashboard {
        return transaction {
            UserProjectDashboardTable.insert {
                it[UserProjectDashboardTable.userId] = userId
                it[UserProjectDashboardTable.projectId] = projectId
                it[UserProjectDashboardTable.widgetIds] = ""
            }.let {
                convertRowToUserProjectDashboard(it)
            }
        }
    }

    fun setOrderedWidgets(userId: Long, projectId: Long, orderedWidgetIds: MutableList<Long>) {
        transaction {
            UserProjectDashboardTable.update(where = {
                UserProjectDashboardTable.userId.eq(userId).and(UserProjectDashboardTable.projectId.eq(projectId))
            }) {
                it[UserProjectDashboardTable.widgetIds] = orderedWidgetIds.joinToString(",")
            }
        }
    }

    private fun convertRowToUserProjectDashboard(row: InsertStatement<Number>): UserProjectDashboard {
        return UserProjectDashboard(
            userId = row[UserProjectDashboardTable.userId],
            projectId = row[UserProjectDashboardTable.projectId],
            orderedWidgetIds = row[UserProjectDashboardTable.widgetIds].split(",").map {
                it.toLongOrNull()
            }.filterNotNull().toMutableList()
        )
    }

    private fun convertRowToUserProjectDashboard(row: ResultRow): UserProjectDashboard {
        return UserProjectDashboard(
            userId = row[UserProjectDashboardTable.userId],
            projectId = row[UserProjectDashboardTable.projectId],
            orderedWidgetIds = row[UserProjectDashboardTable.widgetIds].split(",").map {
                it.toLongOrNull()
            }.filterNotNull().toMutableList()
        )
    }
}
