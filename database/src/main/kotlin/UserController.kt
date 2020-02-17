import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class UserController {

    fun getAll(): ArrayList<UserTable> {
        val users: ArrayList<UserTable> = arrayListOf()
        transaction {
            UserTable.selectAll().map {

            }
        }
        return users
    }
}