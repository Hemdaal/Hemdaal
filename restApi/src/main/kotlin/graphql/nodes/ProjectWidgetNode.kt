package graphql.nodes

import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType


data class ProjectWidgetNode(
    val id: Long,
    val projectId: Long,
    val type: ProjectWidgetType,
    val additionalInfo: String
) {

    constructor(widget: ProjectWidget) : this(widget.id, widget.projectId, widget.getType(), widget.getAdditionalInfo())
}
