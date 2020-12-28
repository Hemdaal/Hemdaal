package domains.build

import java.util.concurrent.TimeUnit

data class BuildFilter(
    val startTime: Long,
    val endTime: Long
) {
    companion object {

        fun default() = BuildFilter(
            startTime = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14),
            endTime = System.currentTimeMillis()
        )
    }
}
