package domains.projectManagement

import domains.Collaborator

/**
 * Stages include. Created, Ready for Dev, In Dev,  etc
 */
class FeatureStage(
    val name: String,
    val assignedTo: Collaborator,
    val movedTime: Long
)