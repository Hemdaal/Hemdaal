package domains.widgets

abstract class ProjectWidget(
    val id: Long,
    val projectId: Long
) {
    abstract fun getType(): ProjectWidgetType

    abstract fun getAdditionalInfo(): String
}
