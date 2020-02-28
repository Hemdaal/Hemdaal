package models

data class CommitData(
    val sha: String,
    val authorName: String,
    val authorEmail: String,
    val time: Long
)