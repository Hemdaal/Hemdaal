package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(
    private val dbUrl: String,
    private val dbPort: String,
    private val dbUser: String,
    private val dbPassword: String
) {

    private val dbHost = "jdbc:postgresql://$dbUrl:$dbPort/hemdaal_db"

    init {
        val flyway = Flyway.configure().dataSource(dbHost, dbUser, dbPassword).load()
        flyway.migrate()

        Database.connect(hikari())
        transaction {
            SchemaUtils.create(
                UserTable,
                ProjectCollaboratorTable,
                CollaboratorTable,
                ProjectTable,
                SoftwareComponentTable,
                BuildManagementTable,
                ProjectManagementTable,
                CodeManagementTable,
                CommitTable

            )
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbHost
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}