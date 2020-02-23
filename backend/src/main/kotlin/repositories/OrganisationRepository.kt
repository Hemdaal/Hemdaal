package repositories

import db.OrganisationTable
import domains.Organisation
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrganisationRepository {

    fun getOrganisationBy(id: Long): Organisation? {
        return transaction {
            OrganisationTable.select(where = { OrganisationTable.id eq id }).singleOrNull()?.let {
                convertRowToOrganisation(it)
            }
        }
    }

    fun getOrganisationBy(ids: List<Long>): List<Organisation> {
        return transaction {
            OrganisationTable.select(where = { OrganisationTable.id inList ids }).mapNotNull {
                convertRowToOrganisation(it)
            }
        }
    }

    fun getOrganisationBy(name: String): Organisation? {
        return transaction {
            OrganisationTable.select(where = { OrganisationTable.name eq name }).singleOrNull()?.let {
                convertRowToOrganisation(it)
            }
        }
    }

    fun createOrganisation(name: String): Organisation? {
        return transaction {
            OrganisationTable.insert {
                it[OrganisationTable.name] = name
            }
            getOrganisationBy(name)
        }
    }

    private fun convertRowToOrganisation(row: ResultRow) = Organisation(
        id = row[OrganisationTable.id],
        name = row[OrganisationTable.name]
    )

}