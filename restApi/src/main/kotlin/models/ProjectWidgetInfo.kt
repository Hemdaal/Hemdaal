package main.kotlin.models

import domains.widgets.ProjectWidget
import domains.widgets.ProjectWidgetType

data class ProjectWidgetInfo(
    val id: Long,
    val projectId: Long,
    val type: ProjectWidgetType,
    val additionalInfo: String
) {

    constructor(widget: ProjectWidget) : this(widget.id, widget.projectId, widget.getType(), widget.getAdditionalInfo())
}
