package repositories

import db.OrganisationTable
import domains.Organisation
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrganisationRepository {

    fun getOrganisationBy(id: Long): Organisation? {
        val orgRow = transaction {
            OrganisationTable.select(where = { OrganisationTable.id eq id }).singleOrNull()
        }
        return orgRow?.let {
            convertRowToOrganisation(orgRow)
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
        val orgRow = transaction {
            OrganisationTable.select(where = { OrganisationTable.name eq name }).singleOrNull()
        }
        return orgRow?.let {
            convertRowToOrganisation(orgRow)
        }
    }

    private fun convertRowToOrganisation(row: ResultRow) = Organisation(
        id = row[OrganisationTable.id],
        name = row[OrganisationTable.name]
    )
}