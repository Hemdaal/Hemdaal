package graphql.nodes

import domains.codeManagement.CodeManagement
import domains.codeManagement.repo.GitLabRepoTool
import domains.codeManagement.repo.GithubRepoTool
import domains.codeManagement.repo.RepoToolType

data class CodeManagementNode(
    val id: Long,
    val type: RepoToolType,
    val repoUrl: String,
    val token: String?
) {

    companion object {
        fun from(codeManagement: CodeManagement): CodeManagementNode {
            val repoToolType: RepoToolType
            val repoUrl: String = codeManagement.tool.repoUrl
            val token: String?
            val tool = codeManagement.tool
            when (tool) {
                is GithubRepoTool -> {
                    repoToolType = RepoToolType.GITHUB
                    token = tool.token
                }
                is GitLabRepoTool -> {
                    repoToolType = RepoToolType.GITLAB
                    token = tool.token
                }
                else -> throw IllegalArgumentException("Not implemented")
            }

            return CodeManagementNode(codeManagement.id, repoToolType, repoUrl, token)
        }
    }
}
