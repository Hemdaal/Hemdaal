package domains.projectManagement

class ProjectManagement(
    val id: Long,
    val tool: PMTool
) {

    fun addFeatures(features: List<Feature>) {

    }

    fun getFeatures(startTime: Long? = 0L): List<Feature> {
        return emptyList()
    }
}