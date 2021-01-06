package domains.widgets

import di.ServiceLocator
import repositories.ProjectWidgetRepository
import repositories.UserProjectDashboardRepository

class UserProjectDashboard(
    val userId: Long,
    val projectId: Long,
    val orderedWidgetIds: MutableList<Long>
) {

    private val userProjectDashboardRepository: UserProjectDashboardRepository =
        ServiceLocator.userProjectDashboardRepository
    private val projectWidgetRepository: ProjectWidgetRepository = ServiceLocator.projectWidgetRepository

    fun getOrderedProjectWidgets(): List<ProjectWidget> {
        return projectWidgetRepository.getProjectWidgets(orderedWidgetIds)
    }

    fun addWidget(widget: ProjectWidget): ProjectWidget {
        val widget = projectWidgetRepository.addProjectWidget(projectId, widget)
        orderedWidgetIds.add(widget.id)
        userProjectDashboardRepository.setOrderedWidgets(userId, projectId, orderedWidgetIds)

        return widget
    }

    fun removeWidget(widget: ProjectWidget) {
        TODO()
    }

    fun orderWidgets(widgetIds: Long) {
        userProjectDashboardRepository.setOrderedWidgets(userId, projectId, orderedWidgetIds)
    }
}
