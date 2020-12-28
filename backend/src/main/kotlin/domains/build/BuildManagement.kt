package domains.build

class BuildManagement(
    val id: Long,
    val buildTool: BuildTool
) {
    fun getBuilds(): List<Build> {
        return emptyList()
    }
}
