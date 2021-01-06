package domains.widgets

class CommitWidget(
    id: Long,
    projectId: Long,
    val softwareId: Long
) : ProjectWidget(id, projectId) {

    constructor(id: Long, projectId: Long, additionalInfo: String) : this(id, projectId, additionalInfo.toLong())

    override fun getAdditionalInfo(): String {
        return softwareId.toString()
    }

    override fun getType(): ProjectWidgetType {
        return ProjectWidgetType.COMMIT
    }
}
