package domains.widget


data class OverallWidget(
    val featuresOpen: Int,
    val featuresClosed: Int,
    val commitCount: Int,
    val openMrs: Int,
    val closedMrs: Int,
    val buildCount: Int,
    val latestBuildStatus: Int
)