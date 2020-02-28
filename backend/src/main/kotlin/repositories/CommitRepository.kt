package repositories

import db.CommitTable
import domains.metric.repo.Commit
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select

class CommitRepository {

    fun addCommits(metricId: Long, commits: List<Commit>) {
        CommitTable.batchInsert(data = commits, ignore = true, body = {
            this[CommitTable.sha] = it.sha
            this[CommitTable.authorId] = it.authorId
            this[CommitTable.time] = it.time
            this[CommitTable.metricId] = metricId
        })
    }

    fun getCommitsByMetricId(metricId: Long): List<Commit> {
        return CommitTable.select(where = { CommitTable.metricId eq metricId }).mapNotNull {
            convertRowToCommit(it)
        }
    }

    private fun convertRowToCommit(row: ResultRow): Commit {
        return Commit(
            sha = row[CommitTable.sha],
            authorId = row[CommitTable.authorId],
            time = row[CommitTable.time]
        )
    }
}