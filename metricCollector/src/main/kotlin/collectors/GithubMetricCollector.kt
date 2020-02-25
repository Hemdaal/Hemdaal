package collectors

import domains.MetricAuthInfo
import domains.metriccollectors.MetricCollector

class GithubMetricCollector(
    resourceUrl: String,
    authInfo: MetricAuthInfo
) : MetricCollector(resourceUrl, authInfo, 0L)