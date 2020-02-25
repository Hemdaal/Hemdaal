package main.kotlin.models

import domains.SoftwareComponent

data class SoftwareComponentInfo(
    val id: Long,
    val name: String
) {
    constructor(softwareComponent: SoftwareComponent) : this(softwareComponent.id, softwareComponent.name)

}