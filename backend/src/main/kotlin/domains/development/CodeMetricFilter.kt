package domains.development

import java.util.concurrent.TimeUnit

data class CodeMetricFilter(
    val startTime: Long,
    val endTime: Long
) {
    companion object {

        fun default() = CodeMetricFilter(
            startTime = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14),
            endTime = System.currentTimeMillis()
        )
    }
}
