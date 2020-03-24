package main.kotlin.models

import domains.development.Commit


data class CommitInfo(
    val sha: String,
    val time: Long,
    val collaboratorInfo: CollaboratorInfo
) {
    constructor(commit: Commit) : this(commit.sha, commit.time, CollaboratorInfo(0, "", ""))
}