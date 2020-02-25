package services

import ServiceLocator
import domains.User
import repositories.UserRepository
import utils.HashUtils

class UserService {

    private val userRepository: UserRepository = ServiceLocator.userRepository
    private val hashUtils: HashUtils = ServiceLocator.hashUtils

    fun createUser(name: String, email: String, password: String): Boolean {
        if (userRepository.isEmailExist(email)) {
            return false
        }

        val passwordHash = hashUtils.sha256(password)
        userRepository.createUser(name, email, passwordHash)

        return true
    }

    fun getUserBy(email: String, password: String): User? {
        val passwordHash = hashUtils.sha256(password)
        return userRepository.returnUserByCheckingEmailAndPassword(email, passwordHash)
    }

    fun getUserBy(email: String): User? {
        return userRepository.returnUserByEmail(email)
    }
}