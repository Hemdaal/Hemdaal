package main.kotlin.models

import domains.project.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(softwareComponent.id, softwareComponent.name)

}