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

object ProjectManagementTable : Table(name = "pm_tool") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val url: Column<String> = varchar("url", 1000)
    val type: Column<String> = varchar("type", 100)
    val token: Column<String?> = varchar("token", 100).nullable()
    val project_id: Column<Long> = long("project_id").references(ProjectTable.id)
}

object CodeManagementTable : Table(name = "git_tool") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val url: Column<String> = varchar("url", 1000)
    val type: Column<String> = varchar("type", 100)
    val token: Column<String?> = varchar("token", 100).nullable()
    val lastSynced: Column<Long?> = long("last_synced").nullable()
    val softwareId: Column<Long> = long("software_id").uniqueIndex().references(SoftwareComponentTable.id)
}

object BuildManagementTable : Table(name = "build_management") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val url: Column<String> = varchar("url", 1000)
    val type: Column<String> = varchar("type", 100)
    val token: Column<String?> = varchar("token", 100).nullable()
    val softwareId: Column<Long> = long("software_id").uniqueIndex().references(SoftwareComponentTable.id)
}

object SoftwareComponentTable : Table(name = "software_component") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val projectId: Column<Long> = long("proj_id").references(ProjectTable.id)
}

object CommitTable : Table(name = "commit") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val softwareId: Column<Long> = long("software_id").references(SoftwareComponentTable.id)
    val sha: Column<String> = varchar("sha", 1000)
    val message: Column<String> = varchar("message", 1000)
    val authorId: Column<Long?> = long("author_id").nullable()
    val time: Column<Long> = long("time")
}

object AuthorTable : Table(name = "author") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String?> = varchar("name", 1000).nullable()
    val email: Column<String?> = varchar("email", 1000).nullable()
}

object BuildTable : Table(name = "build") {
    val id: Column<String> = varchar("id", 1000).primaryKey()
    val status: Column<Boolean> = bool("staus")
    val time: Column<Long> = long("time")
}

object UserProjectDashboardTable : Table("project_dashboard") {
    val userId: Column<Long> = long("user_id").references(UserTable.id)
    val projectId: Column<Long> = long("project_id").references(ProjectTable.id)
    val widgetIds: Column<String> = varchar("widget_ids", 1000)
}

object ProjectWidgetTable : Table(name = "project_widget") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val type: Column<String> = varchar("type", 100)
    val userId: Column<Long> = long("user_id").references(UserTable.id)
    val projectId: Column<Long> = long("project_id").references(ProjectTable.id)
    val additionalInfo: Column<String?> = varchar("additional_info", 1000).nullable()
}
