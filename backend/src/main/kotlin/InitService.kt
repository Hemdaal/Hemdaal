import db.DatabaseFactory

class InitService {

    fun init(dbUrl: String, dbPort: String, dbUser: String, dbPassword: String) {
        DatabaseFactory(dbUrl, dbPort, dbUser, dbPassword)
    }
}