package domains.dashboard.widgets

abstract class ProjectWidget(
    val id: Long,
    val name: String,
    val projectId: Long
) {
    abstract fun getType(): ProjectWidgetType

    abstract fun getAdditionalInfo(): String
}
