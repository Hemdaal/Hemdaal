package repositories

import db.CollaboratorTable
import domains.user.Collaborator
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class CollaboratorRepository {

    fun getCollaboratorByIds(ids: List<Long>): List<Collaborator> {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.id inList ids }).mapNotNull {
                convertRowToCollaborator(it)
            }
        }
    }

    fun getCollaboratorById(id: Long): Collaborator? {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.id eq id }).singleOrNull()?.let {
                convertRowToCollaborator(it)
            }
        }
    }

    fun getCollaboratorByEmails(emails: List<String>): Map<String, Collaborator> {
        return transaction {
            CollaboratorTable.select(where = { CollaboratorTable.email inList emails }).associate {
                it[CollaboratorTable.email] to convertRowToCollaborator(it)
            }
        }
    }

    fun getCollaboratorByEmail(email: String): Collaborator? {
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
            } ?: let {
                CollaboratorTable.insert {
                    it[CollaboratorTable.name] = name.trim()
                    it[CollaboratorTable.email] = email.trim()
                }.let {
                    convertRowToCollaborator(it)
                }
            }
        }
    }


    fun addCollaborators(nonExistingNameEmailMap: Map<String, String>): Map<String, Collaborator> {
        return transaction {
            CollaboratorTable.batchInsert(data = nonExistingNameEmailMap.values, ignore = true) {
                this[CollaboratorTable.name] = nonExistingNameEmailMap[it] ?: ""
                this[CollaboratorTable.email] = it
            }.associate {
                it[CollaboratorTable.email] to convertRowToCollaborator(it)
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
