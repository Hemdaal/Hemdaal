package models

import models.repo.Repo

class Project(
    var name : String
) {
    var repositories : MutableSet<Repo> = mutableSetOf()
    var teams : MutableSet<Team> = mutableSetOf()

    fun addRepo(repo: Repo) {
        repositories.add(repo)
    }

    fun addTeam(team: Team) {
        teams.add(team)
    }

    fun getRepos() = repositories.toMutableList()

    fun getTeams() = teams.toMutableList()
}