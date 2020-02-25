package domains

class Project(
    val id: Long,
    val name: String
) {
    fun getSoftwareComponents(): List<SoftwareComponent> {
        return emptyList()
    }

    fun createSoftwareComponent(name: String) {
        //TODO
    }
}