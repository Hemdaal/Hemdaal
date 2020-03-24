package domains.project

import di.ServiceLocator
import domains.build.BuildManagement
import domains.build.BuildToolType
import domains.development.CodeManagement
import domains.development.GITToolType

class SoftwareComponent(
    val id: Long,
    val name: String
) {

    private val gitToolRepository = ServiceLocator.gitToolRepository
    private val buildToolRepository = ServiceLocator.buildToolRepository

    fun setCodeManagement(
        gitToolType: GITToolType,
        repoUrl: String,
        token: String?
    ): CodeManagement {
        return CodeManagement(
            gitToolRepository.addGITTool(
                id,
                repoUrl,
                gitToolType,
                token
            )
        )
    }

    fun getCodeManagement(): CodeManagement? {
        return gitToolRepository.getGITTool(id)?.let {
            CodeManagement(it)
        }
    }

    fun setBuildManagement(
        buildToolType: BuildToolType,
        repoUrl: String,
        token: String?
    ): BuildManagement {
        return BuildManagement(
            buildToolRepository.addBuildTool(
                id,
                repoUrl,
                buildToolType,
                token
            )
        )
    }

    fun getBuildManagement(): BuildManagement? {
        return buildToolRepository.getBuildTool(id)?.let {
            BuildManagement(it)
        }
    }
}