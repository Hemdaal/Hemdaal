package domains.development

class GITTool(
    val id: Long,
    val type: GITToolType,
    val repoUrl: String,
    val token: String?
)