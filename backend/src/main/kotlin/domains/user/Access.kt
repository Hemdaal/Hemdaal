package domains.user

class Access(
    val scopes: List<Scope>
) {

    companion object {

        fun createOwnerAccess() = Access(
            listOf(
                Scope.ADD_REMOVE_USER,
                Scope.EDIT_PROJECT,
                Scope.VIEW_PROJECT
            )
        )

        fun createMemberAccess() = Access(
            listOf(
                Scope.VIEW_PROJECT
            )
        )
    }
}