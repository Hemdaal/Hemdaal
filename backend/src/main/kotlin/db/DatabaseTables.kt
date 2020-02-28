package db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table(name = "user") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val passwordHash: Column<String> = varchar("hash", 500)
}

object ProjectTable : Table(name = "project") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
}

object CollaboratorTable : Table(name = "collaborator") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100).uniqueIndex()
}

object ProjectCollaboratorTable : Table(name = "project_collaborator") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val collaboratorId: Column<Long> = long("collaborator_id").references(CollaboratorTable.id)
    val projectId: Column<Long> = long("project_id").references(ProjectTable.id)
    val scopes: Column<String> = varchar("scopes", 1000)
}

object SoftwareComponentTable : Table(name = "software_component") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val projectId: Column<Long> = long("proj_id").references(ProjectTable.id)
}

object MetricTable : Table(name = "metric") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val type: Column<String> = varchar("type", 100)
    val resourceUrl: Column<String> = varchar("resource_url", 1000)
    val authToken: Column<String> = varchar("auth_token", 1000)
    val lastSynced: Column<Long> = long("last_synced")
    val collectorType: Column<String> = varchar("collector_type", 100)
    val softwareId: Column<Long> = long("software_id").references(SoftwareComponentTable.id)
}

object CommitTable : Table(name = "commit") {
    val sha: Column<String> = varchar("sha", 1000).primaryKey()
    val authorId: Column<Long> = long("author_id")
    val time: Column<Long> = long("time")
    val metricId: Column<Long> = long("metric_id")
}