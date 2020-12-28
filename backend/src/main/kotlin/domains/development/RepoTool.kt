package domains.development

abstract class RepoTool(
    val repoUrl: String
) {
    abstract fun collect()

    abstract fun getCommits(codeMetricFilter: CodeMetricFilter = CodeMetricFilter.default()): List<Commit>

    abstract fun getMRs(codeMetricFilter: CodeMetricFilter = CodeMetricFilter.default()): List<MergeRequest>
}
