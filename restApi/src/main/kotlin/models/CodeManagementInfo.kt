package main.kotlin.models

import domains.development.CodeManagement
import domains.development.RepoToolType
import domains.development.repo.GitLabRepoTool
import domains.development.repo.GithubRepoTool

data class CodeManagementInfo(
    val id: Long,
    val type: RepoToolType,
    val repoUrl: String,
    val token: String?
) {

    companion object {
        fun from(codeManagement: CodeManagement): CodeManagementInfo {
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

            return CodeManagementInfo(codeManagement.id, repoToolType, repoUrl, token)
        }
    }
}
