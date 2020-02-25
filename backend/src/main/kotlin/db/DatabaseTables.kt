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

object SoftwareComponentTable : Table(name = "software_component") {
    val id: Column<Long> = ProjectTable.long("id").autoIncrement().primaryKey()
    val name: Column<String> = ProjectTable.varchar("name", 100)
    val projectId: Column<Long> = long("proj_id").references(ProjectTable.id)
}

object MetricComponentTable : Table(name = "metric_compnent") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val type: Column<String> = varchar("type", 100)
    val softwareId: Column<Long> = long("soft_id").references(SoftwareComponentTable.id)
}

object MetricCollectorTable : Table(name = "metric_collector") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val metricId: Column<Long> = long("metric_id").references(MetricComponentTable.id)
    val type: Column<String> = varchar("type", 100)
    val resourceUrl: Column<String> = varchar("resource_url", 1000)
    val authInfo: Column<String> = varchar("auth_info", 1000)
    val lastSyncTime: Column<Long> = long("last_synced_time")
}

object RepositoryMetricTable : Table(name = "repository_metric") {
    val id: Column<Long> = long("id").references(MetricComponentTable.id)
}

object CommitTable : Table(name = "commit") {
    val sha: Column<String> = varchar("sha", 1000).primaryKey()
    val authorId: Column<Long> = long("author_id").references(CollaboratorTable.id)
    val time: Column<Long> = long("time")
    val metricId: Column<Long> = long("metric_id").references(RepositoryMetricTable.id)
}