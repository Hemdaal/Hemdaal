package domains.widget

import domains.build.BuildManagement
import domains.development.CodeManagement
import domains.projectManagement.ProjectManagement

class WidgetManagement(
    val projectManagement: ProjectManagement?,
    val codeManagement: CodeManagement?,
    val buildManagement: BuildManagement?
) {

    fun getOverallWidget(): OverallWidget {
        TODO()
    }
}