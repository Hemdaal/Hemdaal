package graphql.nodes

import domains.dashboard.UserProjectDashboard
import domains.dashboard.widgets.ProjectWidgetType
import graphql.nodes.widgets.WidgetNode
import main.kotlin.models.WidgetProvider

data class UserProjectDashboardNode(
    val userId: Long,
    val projectId: Long,
    val orderedWidgets: List<ProjectWidgetNode>
) {

    constructor(userProjectDashboard: UserProjectDashboard) : this(
        userId = userProjectDashboard.userId,
        projectId = userProjectDashboard.projectId,
        orderedWidgets = userProjectDashboard.getOrderedProjectWidgets().map {
            ProjectWidgetNode(it)
        })

    fun addWidget(type: ProjectWidgetType, additionalInfo: String?): ProjectWidgetNode {
        return ProjectWidgetNode(
            UserProjectDashboard(userId, projectId).addWidget(
                type = type,
                additionalInfo = additionalInfo
            )
        )
    }

    fun widgets(): List<WidgetNode> {
        return UserProjectDashboard(userId, projectId).getOrderedProjectWidgets().map {
            WidgetNode(it)
        }
    }

    fun widget(id: Long): WidgetNode? {
        return UserProjectDashboard(userId, projectId).getWidget(id)?.let {
            WidgetProvider.provide(it)
        }
    }
}
