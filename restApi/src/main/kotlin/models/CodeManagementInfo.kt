package main.kotlin.models

import domains.development.CodeManagement
import domains.development.GITToolType

data class CodeManagementInfo(
    val id: Long,
    val type: GITToolType,
    val repoUrl: String,
    val token: String?
) {
    constructor(codeManagement: CodeManagement) : this(
        codeManagement.id,
        codeManagement.tool.type,
        codeManagement.tool.repoUrl,
        codeManagement.tool.token
    )
}