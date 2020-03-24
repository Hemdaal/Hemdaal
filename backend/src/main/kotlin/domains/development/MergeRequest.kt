package domains.development

class MergeRequest(
    val id: String,
    val fromBranch: String,
    val toBranch: String,
    val merged: Boolean,
    val time: Long
)