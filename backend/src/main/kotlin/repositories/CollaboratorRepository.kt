package repositories

import db.CollaboratorTable
import db.UserTable
import domains.Collaborator
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class CollaboratorRepository {

    fun getCollaboratorBy(ids: List<Long>): List<Collaborator> {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.id inList ids }).mapNotNull {
                convertRowToCollaborator(it)
            }
        }
    }

    fun getCollaboratorBy(email: String): Collaborator? {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.email eq email }).singleOrNull()?.let {
                convertRowToCollaborator(it)
            }
        }
    }

    fun getOrCreateCollaborator(
        name: String,
        email: String
    ): Collaborator {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.email eq email }).singleOrNull()?.let {
                convertRowToCollaborator(it)
            }.let {
                CollaboratorTable.insert {
                    it[UserTable.name] = name.trim()
                    it[UserTable.email] = email.trim()
                }.let {
                    convertRowToCollaborator(it)
                }
            }
        }
    }

    private fun convertRowToCollaborator(row: InsertStatement<Number>): Collaborator {
        return Collaborator(
            id = row[CollaboratorTable.id],
            name = row[CollaboratorTable.name],
            email = row[CollaboratorTable.email]
        )
    }

    private fun convertRowToCollaborator(row: ResultRow): Collaborator {
        return Collaborator(
            id = row[CollaboratorTable.id],
            name = row[CollaboratorTable.name],
            email = row[CollaboratorTable.email]
        )
    }

}