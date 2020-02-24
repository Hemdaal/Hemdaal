package db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table(name = "user") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val passwordHash: Column<String> = varchar("hash", 500)
}

object OrganisationTable : Table(name = "organisation") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
}

object CollaboratorTable : Table(name = "collaborator") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100).uniqueIndex()
}

object OrgCollaboratorTable : Table(name = "org_collaborator") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val colId: Column<Long> = long("col_id").references(CollaboratorTable.id)
    val orgId: Column<Long> = long("org_id").references(OrganisationTable.id)
    val scopes: Column<String> = varchar("scopes", 1000)
}

object ProjectTable : Table(name = "project") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val organisationId: Column<Long> = long("org_id").references(OrganisationTable.id)
}