package graphql.nodes.widgets

import domains.dashboard.widgets.ProjectWidget
import domains.dashboard.widgets.ProjectWidgetType

open class WidgetNode(
    val id: Long,
    val name: String,
    val projectId: Long,
    val type: ProjectWidgetType,
    val additionalInfo: String
) {
    constructor(widget: ProjectWidget) : this(
        widget.id,
        widget.name,
        widget.projectId,
        widget.getType(),
        widget.getAdditionalInfo()
    )
}
