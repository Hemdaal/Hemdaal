import db.DatabaseFactory
import domains.collectors.CollectorRunner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object InitService {

    fun initCollectors() {
        val collectorRunner: CollectorRunner = CollectorRunner()

        GlobalScope.launch {
            while (true) {
                delay(TimeUnit.HOURS.toMillis(1))

                collectorRunner.fetchAndRunCollectors()
            }
        }
    }

    fun initDB(dbUrl: String, dbPort: String, dbUser: String, dbPassword: String) {
        DatabaseFactory(dbUrl, dbPort, dbUser, dbPassword)
    }
}
