package domains.metriccollectors

import domains.MetricAuthInfo

class GithubMetricCollector(
    resourceUrl: String,
    authInfo: MetricAuthInfo
) : MetricCollector(resourceUrl, authInfo, 0L)