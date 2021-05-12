package domains.dashboard

import di.ServiceLocator
import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType

class UserProjectDashboard(
    val userId: Long,
    val projectId: Long
) {

    private val userProjectDashboardRepository by lazy {
        ServiceLocator.userProjectDashboardRepository
    }

    private val projectWidgetRepository by lazy {
        ServiceLocator.projectWidgetRepository
    }

    fun getOrderedProjectWidgets(): List<ProjectWidget> {
        val widgetIds = getOrderedWidgetIds()
        val widgets = projectWidgetRepository.getProjectWidgets(widgetIds, userId)

        val orderById = widgetIds.withIndex().associate { it.value to it.index }
        return widgets.sortedBy { orderById[it.id] }
    }

    fun addWidget(type: ProjectWidgetType, additionalInfo: String): ProjectWidget {
        val widgetIds = getOrderedWidgetIds().toMutableList()
        val widget = projectWidgetRepository.addProjectWidget(
            projectId = projectId,
            userId = userId,
            type = type,
            additonalInfo = additionalInfo
        )
        widgetIds.add(widget.id)
        userProjectDashboardRepository.setOrderedWidgets(
            userId = userId,
            projectId = projectId,
            orderedWidgetIds = widgetIds
        )

        return widget
    }

    fun getWidget(widgetId: Long): ProjectWidget? {
        return projectWidgetRepository.getProjectWidget(widgetId, userId)
    }

    fun removeWidget(id: Long) {
        val widgetIds = getOrderedWidgetIds().toMutableList()
        widgetIds.remove(id)
        orderWidgets(widgetIds)
        projectWidgetRepository.removeWidget(id = id, userId = userId)
    }

    fun orderWidgets(widgetIds: List<Long>) {
        userProjectDashboardRepository.setOrderedWidgets(
            userId = userId,
            projectId = projectId,
            orderedWidgetIds = widgetIds
        )
    }

    private fun getOrderedWidgetIds() = userProjectDashboardRepository.getOrderedWidgets(
        userId = userId,
        projectId = projectId
    )
}
