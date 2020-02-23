package domains

enum class Scope {
    CREATE_PROJECT,
    CREATE_WIDGET,
    DELETE_PROJECT,
    DELETE_WIDGET,
    ADD_USER,
    REMOVE_USER
}

fun allScopes(): List<Scope> = listOf(
    Scope.CREATE_PROJECT,
    Scope.CREATE_WIDGET,
    Scope.DELETE_PROJECT,
    Scope.DELETE_WIDGET,
    Scope.ADD_USER,
    Scope.REMOVE_USER
)