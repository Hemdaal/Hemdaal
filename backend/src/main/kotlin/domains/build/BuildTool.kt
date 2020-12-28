package domains.build

abstract class BuildTool(
    val url: String,
    val type: BuildToolType,
    val token: String?
) {
    abstract fun collect()

    abstract fun getBuildStatus(buildFilter: BuildFilter = BuildFilter.default())
}
