package graphql.nodes.widgets

import domains.dashboard.widgets.CommitByDayWidget
import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType
import java.util.*

class CommitByDayWidgetNode(
    id: Long,
    name: String,
    projectId: Long,
    additionalInfo: String
) : WidgetNode(id, name, projectId, ProjectWidgetType.COMMIT_BY_DAY, additionalInfo) {

    constructor(widget: ProjectWidget) : this(widget.id, widget.name, widget.projectId, widget.getAdditionalInfo())

    fun getMetrics(): Map<Date, Int> {
        return CommitByDayWidget(id, name, projectId, additionalInfo).getMetrics()
    }
}
