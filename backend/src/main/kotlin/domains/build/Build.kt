package domains.build

class Build(
    val id: String,
    val createdAt: Long,
    val status: Boolean,
    val endedAt: Long
)