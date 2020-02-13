package models.repo

import models.Collaborator

class Author(
    name: String,
    email: String
) : Collaborator(
    name = name,
    email = email
) {


}