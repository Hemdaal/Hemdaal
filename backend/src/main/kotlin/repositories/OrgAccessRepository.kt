package repositories

import db.OrgUserAccessTable
import domains.Scope
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrgAccessRepository {

    fun getUserAccess(orgId: Long): Map<Long, List<Scope>> {
        val userScopeMap = mutableMapOf<Long, MutableList<Scope>>()
        transaction {
            OrgUserAccessTable.select(where = { OrgUserAccessTable.orgId eq orgId }).forEach {
                addScope(userScopeMap, it)
            }
        }

        return userScopeMap
    }

    fun getOrganisations(userId: Long): List<Long> {
        return transaction {
            OrgUserAccessTable.select(where = { OrgUserAccessTable.userId eq userId }).mapNotNull {
                it[OrgUserAccessTable.orgId]
            }
        }
    }

    private fun addScope(userScopeMap: MutableMap<Long, MutableList<Scope>>, row: ResultRow) {
        val userId: Long = row[OrgUserAccessTable.userId]
        val scopeString: String = row[OrgUserAccessTable.scopes]
        val scopes = scopeString.split(",")
        userScopeMap[userId] = userScopeMap.getOrDefault(userId, mutableListOf()).also { scopeList ->
            scopes.forEach { scopeStr ->
                scopeList.add(Scope.valueOf(scopeStr))
            }
        }
    }
}