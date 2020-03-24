package repositories

import db.CommitTable
import domains.development.Commit
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

class CommitRepository {

    fun addCommits(metricId: Long, commits: List<Commit>) {
        transaction {
            CommitTable.batchInsert(data = commits, ignore = true, body = {
                this[CommitTable.sha] = it.sha
                this[CommitTable.authorId] = it.authorId
                this[CommitTable.time] = it.time
            })
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