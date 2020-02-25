package domains

class Project(
    val id: Long,
    val name: String
) {
    private val softwareComponentRepository = ServiceLocator.softwareComponentRepository

    fun getSoftwareComponents(): List<SoftwareComponent> {
        return softwareComponentRepository.getSoftwareComponentsBy(id)
    }

    fun createSoftwareComponent(name: String): SoftwareComponent {
        return softwareComponentRepository.createSoftwareComponent(name, id)
    }
}