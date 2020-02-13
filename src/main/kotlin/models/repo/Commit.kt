package models.repo

import models.repo.Author

data class Commit(
    val sha : String,
    val message : String,
    val treeSha : String,
    val committedAt : Long,
    val author: Author
) {

}