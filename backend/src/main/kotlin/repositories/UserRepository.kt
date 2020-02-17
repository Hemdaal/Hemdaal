package repositories

import db.UserTable
import domains.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

class UserRepository {

    fun getUserBy(ids: List<Long>): List<User> {
        return UserTable.select(where = { UserTable.id inList ids }).mapNotNull {
            createUser(it)
        }
    }

    private fun createUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id],
            name = row[UserTable.name],
            email = row[UserTable.email]
        )
    }
}