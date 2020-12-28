package repositories

import db.CodeManagementTable
import domains.development.CodeManagement
import domains.development.RepoToolType
import domains.development.RepoToolType.*
import domains.development.repo.GitLabRepoTool
import domains.development.repo.GithubRepoTool
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

class CodeManagementRepository {

    fun addCodeManagement(
        softwareId: Long,
        url: String,
        type: RepoToolType,
        token: String?
    ) = CodeManagementTable.insert {
        it[CodeManagementTable.softwareId] = softwareId
        it[CodeManagementTable.url] = url
        it[CodeManagementTable.token] = token
        it[CodeManagementTable.type] = type.name
    }.let {
        convertRowToCodeManagement(it)
    }

    fun getCodeManagement(softwareId: Long) =
        CodeManagementTable.select(where = { CodeManagementTable.softwareId eq softwareId }).singleOrNull()?.let {
            convertRowToCodeManagement(it)
        }

    fun setLastSynced(id: Long, lastSynced: Long) {
        CodeManagementTable.update(where = { CodeManagementTable.id eq id }) {
            it[CodeManagementTable.lastSynced] = lastSynced
        }
    }

    private fun convertRowToCodeManagement(row: ResultRow): CodeManagement {
        val repoToolType = valueOf(row[CodeManagementTable.type])
        val repoTool = when (repoToolType) {
            GITHUB -> GithubRepoTool(
                row[CodeManagementTable.url],
                row[CodeManagementTable.token]
            )
            GITLAB -> GitLabRepoTool(
                row[CodeManagementTable.url],
                row[CodeManagementTable.token]
            )
        }
        return CodeManagement(
            id = row[CodeManagementTable.id],
            tool = repoTool,
            lastSynced = row[CodeManagementTable.lastSynced] ?: 0L
        )
    }

    private fun convertRowToCodeManagement(row: InsertStatement<Number>): CodeManagement {
        val repoToolType = valueOf(row[CodeManagementTable.type])
        val repoTool = when (repoToolType) {
            GITHUB -> GithubRepoTool(
                row[CodeManagementTable.url],
                row[CodeManagementTable.token]
            )
            GITLAB -> GitLabRepoTool(
                row[CodeManagementTable.url],
                row[CodeManagementTable.token]
            )
        }
        return CodeManagement(
            id = row[CodeManagementTable.id],
            tool = repoTool,
            lastSynced = row[CodeManagementTable.lastSynced] ?: 0L
        )
    }

}
