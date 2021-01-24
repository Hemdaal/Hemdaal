package gitlab

import com.google.gson.annotations.SerializedName

data class GitlabCommitDTO(
    @SerializedName("id") val sha: String,
    @SerializedName("commited_date") val commitedDate: String,
    val message: String,
    @SerializedName("author_name") val authorName: String?,
    @SerializedName("author_email") val authorEmail: String?
) {
    fun getCommitTime(): Long {
        TODO("Not yet implemented")
    }
}
