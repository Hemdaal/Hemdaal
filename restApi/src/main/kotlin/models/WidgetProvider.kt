package main.kotlin.models

import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType
import graphql.nodes.widgets.CommitByDayWidgetNode
import graphql.nodes.widgets.WidgetNode

object WidgetProvider {

    fun provide(widget: ProjectWidget): WidgetNode {
        return when (widget.getType()) {
            ProjectWidgetType.COMMIT_BY_DAY -> CommitByDayWidgetNode(widget)
        }
    }
}
