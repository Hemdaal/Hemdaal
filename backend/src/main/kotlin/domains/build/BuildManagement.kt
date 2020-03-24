package domains.build

class BuildManagement(
    val id: Long,
    val buildTool: BuildTool
) {
    fun addBuild(build: Build) {
        //TODO
    }

    fun getBuilds(): List<Build> {
        return emptyList()
    }
}