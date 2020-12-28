package domains.project

abstract class PMTool(
    val url: String
) {
    abstract fun collect()

    abstract fun getTasks(): List<Taskable>
}
