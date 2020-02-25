package domains

sealed class MetricAuthInfo {

    class Basic(val userName: String, val password: String) : MetricAuthInfo()

    class Token(val token: String) : MetricAuthInfo()
}
