package graphql.nodes

import domains.user.Access
import domains.user.Scope

data class AccessNode(
    val scopes: List<Scope>
) {
    constructor(access: Access) : this(access.scopes)
}
