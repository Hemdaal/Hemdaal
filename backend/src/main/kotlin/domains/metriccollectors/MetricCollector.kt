package domains.metriccollectors

import domains.MetricAuthInfo

abstract class MetricCollector(
    var resourceUrl: String,
    var authInfo: MetricAuthInfo,
    var lastSyncTime: Long = 0L
)