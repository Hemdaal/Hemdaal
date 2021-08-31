package domains.dashboard.widgets

import di.ServiceLocator
import java.util.*

class CommitByDayWidget(
    id: Long,
    name: String,
    projectId: Long,
    val softwareId: Long?
) : ProjectWidget(id, name, projectId) {

    private val commitRepository by lazy {
        ServiceLocator.commitRepository
    }

    constructor(id: Long, name: String, projectId: Long, additionalInfo: String?) : this(
        id,
        name,
        projectId,
        additionalInfo?.toLong()
    )

    override fun getType() = ProjectWidgetType.COMMIT_BY_DAY

    override fun getAdditionalInfo(): String {
        return softwareId.toString()
    }

    fun getMetrics(): Map<Date, Int> {
        TODO()
    }
}
