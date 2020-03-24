package domains.build

class Build(
    val id: String,
    val buildStageStatuses: List<BuildStageStatus>,
    val createdAt: Long,
    val endedAt: Long
)