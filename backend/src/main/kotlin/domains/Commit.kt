package domains

class Commit(
    val sha: String,
    val authorName: String,
    val authorEmail: String,
    val time: Long
)