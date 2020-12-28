package domains.collectors

import domains.collectors.code.CodeManagementCollector

class CollectorRunner {

    fun fetchAndRunCollectors() {
        CodeManagementCollector().collect()
        //TODO ass BuildCollectors....
    }
}
