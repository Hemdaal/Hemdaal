import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object User : Table(name = "user") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100)
    val passwordHash: Column<String> = varchar("hash", 500)
}

object Organisation : Table(name = "organisation") {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
}