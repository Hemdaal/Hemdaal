package repositories

import db.OrgCollaboratorTable
import domains.Scope
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrgCollaboratorRepository {

    fun getCollaboratorAccess(orgId: Long): Map<Long, List<Scope>> {
        val userScopeMap = mutableMapOf<Long, MutableList<Scope>>()
        transaction {
            OrgCollaboratorTable.select(where = { OrgCollaboratorTable.orgId eq orgId }).forEach {
                addScope(userScopeMap, it)
            }
        }

        return userScopeMap
    }

    fun getOrganisations(colId: Long): List<Long> {
        return transaction {
            OrgCollaboratorTable.select(where = { OrgCollaboratorTable.colId eq colId }).mapNotNull {
                it[OrgCollaboratorTable.orgId]
            }
        }
    }

    fun createCollaboratorAccess(colId: Long, orgId: Long, scopes: List<Scope>) {
        return transaction {
            OrgCollaboratorTable.insert {
                it[OrgCollaboratorTable.orgId] = orgId
                it[OrgCollaboratorTable.colId] = colId
                it[OrgCollaboratorTable.scopes] = scopes.joinToString(",")
            }
        }
    }

    private fun addScope(userScopeMap: MutableMap<Long, MutableList<Scope>>, row: ResultRow) {
        val colId: Long = row[OrgCollaboratorTable.colId]
        val scopeString: String = row[OrgCollaboratorTable.scopes]
        val scopes = scopeString.split(",")
        userScopeMap[colId] = userScopeMap.getOrDefault(colId, mutableListOf()).also { scopeList ->
            scopes.forEach { scopeStr ->
                scopeList.add(Scope.valueOf(scopeStr))
            }
        }
    }
}