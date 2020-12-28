package domains.development

class Commit(
    val sha: String,
    val message: String,
    val authorId: Long,
    val time: Long
)
