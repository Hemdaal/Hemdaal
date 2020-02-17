package repositories

import db.OrgUserAccessTable
import domains.Scope
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

class OrgAccessRepository {

    fun getUserAccess(orgId: Long): Map<Long, List<Scope>> {
        val userScopeMap = mutableMapOf<Long, MutableList<Scope>>()
        OrgUserAccessTable.select(where = { OrgUserAccessTable.orgId eq orgId }).forEach {
            addScope(userScopeMap, it)
        }

        return userScopeMap
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