package repositories

import db.UserTable
import domains.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun getUserBy(ids: List<Long>): List<User> {
        return transaction {
            UserTable.select(where = { UserTable.id inList ids }).mapNotNull {
                convertRowToUser(it)
            }
        }
    }

    fun createUser(
        name: String,
        email: String,
        passwordHash: String
    ) {
        transaction {
            UserTable.insert {
                it[UserTable.name] = name.trim()
                it[UserTable.email] = email.trim()
                it[UserTable.passwordHash] = passwordHash
            }
        }
    }

    fun isEmailExist(email: String): Boolean {
        return transaction { UserTable.select(where = { UserTable.email eq email.trim() }).count() != 0 }
    }

    fun returnUserByEmail(email: String): User? {
        val row = transaction {
            UserTable.select(where = { UserTable.email.eq(email) }).singleOrNull()
        }
        return row?.let {
            convertRowToUser(it)
        }
    }

    fun returnUserByCheckingEmailAndPassword(email: String, passwordHash: String): User? {
        val row = transaction {
            UserTable.select(where = { UserTable.email.eq(email) and UserTable.passwordHash.eq(passwordHash) })
                .singleOrNull()
        }
        return row?.let {
            convertRowToUser(it)
        }
    }

    private fun convertRowToUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id],
            name = row[UserTable.name],
            email = row[UserTable.email]
        )
    }

}