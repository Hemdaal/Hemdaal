package main.kotlin.models

import domains.widgets.ProjectWidgetType
import domains.widgets.UserProjectDashboard

data class UserProjectDashboardInfo(
    val userId: Long,
    val projectId: Long,
    val orderedWidgets: List<ProjectWidgetInfo>
) {

    constructor(userProjectDashboard: UserProjectDashboard) : this(
        userId = userProjectDashboard.userId,
        projectId = userProjectDashboard.projectId,
        orderedWidgets = userProjectDashboard.getOrderedProjectWidgets().map {
            ProjectWidgetInfo(it)
        })

    fun addWidget(type: ProjectWidgetType, additionalInfo: String): ProjectWidgetInfo {
        return ProjectWidgetInfo(
            UserProjectDashboard(userId, projectId).addWidget(
                type = type,
                additionalInfo = additionalInfo
            )
        )
    }

    fun removeWidget(widgetId: Long): Boolean {
        UserProjectDashboard(userId, projectId).removeWidget(widgetId)
        return true
    }
}
