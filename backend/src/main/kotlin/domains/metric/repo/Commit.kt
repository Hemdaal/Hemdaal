package domains.metric.repo

import di.ServiceLocator
import domains.Collaborator
import repositories.CollaboratorRepository

class Commit(
    val sha: String,
    val authorId: Long,
    val time: Long
) {

    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository

    fun getAuthor(): Collaborator? {
        return collaboratorRepository.getCollaboratorById(authorId)
    }
}