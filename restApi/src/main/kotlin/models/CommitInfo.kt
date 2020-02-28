package main.kotlin.models

import domains.metric.repo.Commit

data class CommitInfo(
    val sha: String,
    val time: Long,
    val collaboratorInfo: CollaboratorInfo
) {
    constructor(commit: Commit) : this(commit.sha, commit.time, CollaboratorInfo(0, "", ""))
}