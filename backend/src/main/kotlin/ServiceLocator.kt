@file:Suppress("UNCHECKED_CAST")

import repositories.OrgAccessRepository
import repositories.OrganisationRepository
import repositories.ProjectRepository
import repositories.UserRepository
import utils.HashUtils

object ServiceLocator {

    const val HASH_UTILS = "hash_utils"
    const val ORG_ACCESS_REPOSITORY = "org_access_repository"
    const val USER_REPOSITORY = "user_repository"
    const val PROJECT_REPOSITORY = "project_repository"
    const val ORG_REPOSITORY = "org_repository"

    var mockProviderCallback: ((tag: String) -> Any)? = null

    fun <T> get(tag: String): T {
        return (mockProviderCallback?.invoke(tag) as T?) ?: createObject(tag)
    }

    private fun <T> createObject(tag: String): T {
        return when (tag) {
            PROJECT_REPOSITORY -> ProjectRepository() as T
            ORG_REPOSITORY -> OrganisationRepository() as T
            USER_REPOSITORY -> UserRepository() as T
            ORG_ACCESS_REPOSITORY -> OrgAccessRepository() as T
            HASH_UTILS -> HashUtils as T
            else -> throw IllegalArgumentException("unidentified tag")
        }
    }
}