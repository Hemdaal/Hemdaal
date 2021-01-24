package repositories

import db.CodeManagementTable
import domains.development.CodeManagement
import domains.development.RepoToolType
import domains.development.RepoToolType.*
import domains.development.repo.GitLabRepoTool
import domains.development.repo.GithubRepoTool
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class CodeManagementRepository {

    fun addCodeManagement(
        softwareId: Long,
        url: String,
        type: RepoToolType,
        token: String?
    ): CodeManagement = transaction {
        CodeManagementTable.insert {
            it[CodeManagementTable.softwareId] = softwareId
            it[CodeManagementTable.url] = url
            it[CodeManagementTable.token] = token
            it[CodeManagementTable.type] = type.name
        }.let {
            convertRowToCodeManagement(it)
        }
    }

    fun getCodeManagement(softwareId: Long) = transaction {
        CodeManagementTable.select(where = { CodeManagementTable.softwareId eq softwareId }).singleOrNull()?.let {
            convertRowToCodeManagement(it)
        }
    }

    fun getLastSyncedCodeManagements(lastSynced: Long): List<CodeManagement> {
        return transaction {

            CodeManagementTable.select(where = { (CodeManagementTable.lastSynced.lessEq(lastSynced)) })
                .orderBy(CodeManagementTable.lastSynced, SortOrder.ASC).limit(0, 10).map {
                    convertRowToCodeManagement(it)
                }
        }
    }

    fun setLastSynced(id: Long, lastSynced: Long) {
        transaction {
            CodeManagementTable.update(where = { CodeManagementTable.id eq id }) {
                it[CodeManagementTable.lastSynced] = lastSynced
            }
        }
    }

    private fun convertRowToCodeManagement(row: ResultRow): CodeManagement {
        val repoToolType = valueOf(row[CodeManagementTable.type])
        val repoTool = when (repoToolType) {
            GITHUB -> GithubRepoTool(
                url = row[CodeManagementTable.url],
                softwareId = row[CodeManagementTable.softwareId],
                token = row[CodeManagementTable.token]
            )
            GITLAB -> GitLabRepoTool(
                url = row[CodeManagementTable.url],
                softwareId = row[CodeManagementTable.softwareId],
                token = row[CodeManagementTable.token]
            )
        }
        return CodeManagement(
            id = row[CodeManagementTable.id],
            tool = repoTool,
            lastSynced = row[CodeManagementTable.lastSynced]
        )
    }

    private fun convertRowToCodeManagement(row: InsertStatement<Number>): CodeManagement {
        val repoToolType = valueOf(row[CodeManagementTable.type])
        val repoTool = when (repoToolType) {
            GITHUB -> GithubRepoTool(
                url = row[CodeManagementTable.url],
                softwareId = row[CodeManagementTable.softwareId],
                token = row[CodeManagementTable.token]
            )
            GITLAB -> GitLabRepoTool(
                url = row[CodeManagementTable.url],
                softwareId = row[CodeManagementTable.softwareId],
                token = row[CodeManagementTable.token]
            )
        }
        return CodeManagement(
            id = row[CodeManagementTable.id],
            tool = repoTool,
            lastSynced = row[CodeManagementTable.lastSynced]
        )
    }

}
