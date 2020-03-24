package domains.projectManagement

import domains.Collaborator

class Feature(
    val id: String,
    val name: String,
    val description: String?,
    val stages: List<FeatureStage>,
    val softwareComponentName: String?,
    val milestone: String,
    val createdBy: Collaborator?,
    val createdAt: Long,
    val closedAt: Long
)