package domains.development

class CodeManagement(
    val id: Long,
    val tool: GITTool
) {

    fun addCommits(commits: List<Commit>) {

    }

    fun addMergeRequests(mergeRequests: List<MergeRequest>) {

    }

    fun getCommits(
        startTime: Long? = 0
    ): List<Commit> {
        return emptyList()
    }

    fun getMergeRequests(
        startTime: Long? = 0
    ): List<MergeRequest> {
        return emptyList()
    }
}