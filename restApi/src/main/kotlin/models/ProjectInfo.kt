package main.kotlin.models

import domains.Project

data class ProjectInfo(
    val id: Long,
    val name: String
) {
    constructor(project: Project) : this(project.id, project.name)
}