package models.repo

data class Repo(
    var name : String,
    var url : String,
    var repoAuth: RepoAuth
) {

}