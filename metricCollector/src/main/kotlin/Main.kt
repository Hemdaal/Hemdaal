import di.hemdaalInjectionModule
import di.injectionModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin

fun main(args: Array<String>) {

    startKoin {
        modules(listOf(injectionModule, hemdaalInjectionModule))
    }

    val collectorRunner: CollectorRunner = CollectorRunner()

    GlobalScope.launch {
        while (true) {
            delay(1 * 60 * 60)

            collectorRunner.fetchAndRunCollectors()
        }
    }
}