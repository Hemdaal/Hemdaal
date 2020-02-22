import db.DatabaseFactory

class InitService {

    fun init(dbUrl: String, dbUser: String, dbPassword: String) {
        DatabaseFactory(dbUrl, dbUser, dbPassword)
    }
}