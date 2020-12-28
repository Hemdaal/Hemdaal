import db.DatabaseFactory
import domains.collectors.CollectorRunner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object InitService {

    fun initCollectors() {
        val collectorRunner: CollectorRunner = CollectorRunner()

        GlobalScope.launch {
            while (true) {
                delay(1 * 60 * 60)

                collectorRunner.fetchAndRunCollectors()
            }
        }
    }

    fun initDB(dbUrl: String, dbPort: String, dbUser: String, dbPassword: String) {
        DatabaseFactory(dbUrl, dbPort, dbUser, dbPassword)
    }
}
