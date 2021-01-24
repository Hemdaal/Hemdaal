package domains.collectors.code.gitlab

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class GitlabCommitDTO(
    @SerializedName("id") val sha: String,
    @SerializedName("committed_date") val committedDate: String,
    val message: String,
    @SerializedName("author_name") val authorName: String?,
    @SerializedName("author_email") val authorEmail: String?
) {
    fun getCommitTime(): Long {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(committedDate).time
    }
}
