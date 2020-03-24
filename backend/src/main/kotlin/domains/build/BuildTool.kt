package domains.build

class BuildTool(
    val id: Long,
    val url: String,
    val type: BuildToolType,
    val token: String?
)