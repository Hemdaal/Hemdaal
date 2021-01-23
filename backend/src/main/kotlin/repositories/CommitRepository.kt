package repositories

import db.AuthorTable
import db.CommitTable
import domains.development.Author
import domains.development.Commit
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class CommitRepository {

    fun addCommits(softwareId: Long, commits: List<Commit>) {
        transaction {
            CommitTable.batchInsert(data = commits, ignore = true, body = {
                this[CommitTable.sha] = it.sha
                this[CommitTable.authorId] = it.authorId
                this[CommitTable.time] = it.time
                this[CommitTable.softwareId] = softwareId
            })
        }
    }

    fun getLastCommit(softwareId: Long): Commit? {
        return transaction {
            CommitTable.select(where = { CommitTable.softwareId.eq(softwareId) })
                .orderBy(CommitTable.time, SortOrder.DESC).firstOrNull()?.let {
                    convertRowToCommit(it)
                }
        }
    }

    fun addAuthor(name: String?, email: String?): Author? {
        return transaction {
            AuthorTable.insert {
                it[AuthorTable.email] = email
                it[AuthorTable.name] = name
            }.let {
                convertRowToAuthor(it)
            }
        }
    }

    fun getAuthor(name: String?, email: String?): Author? {
        return transaction {
            AuthorTable.select(where = {
                (AuthorTable.email.eq(email).and(AuthorTable.name.eq(name))).or(AuthorTable.email.eq(email))
                    .or(AuthorTable.name.eq(name))
            }).firstOrNull()?.let {
                convertRowToAuthor(it)
            }
        }
    }

    private fun convertRowToCommit(row: ResultRow): Commit {
        return Commit(
            id = row[CommitTable.id],
            sha = row[CommitTable.sha],
            authorId = row[CommitTable.authorId],
            message = row[CommitTable.message],
            time = row[CommitTable.time]
        )
    }

    private fun convertRowToAuthor(row: ResultRow): Author {
        return Author(
            id = row[AuthorTable.id],
            name = row[AuthorTable.name],
            email = row[AuthorTable.email]
        )
    }

    private fun convertRowToAuthor(row: InsertStatement<Number>): Author {
        return Author(
            id = row[AuthorTable.id],
            name = row[AuthorTable.name],
            email = row[AuthorTable.email]
        )
    }
}
