package main.kotlin.models

import domains.user.Access
import domains.user.Scope

data class AccessInfo(
    val scopes: List<Scope>
) {
    constructor(access: Access) : this(access.scopes)
}