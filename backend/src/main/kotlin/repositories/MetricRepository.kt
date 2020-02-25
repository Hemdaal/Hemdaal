package repositories

import db.MetricTable
import domains.metric.CollectorType
import domains.metric.Metric
import domains.metric.MetricCollectorInfo
import domains.metric.MetricType
import domains.metric.repo.RepoMetric
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class MetricRepository {

    fun addMetric(metricType: MetricType, metricCollectorInfo: MetricCollectorInfo, softwareId: Long): Metric {
        return MetricTable.insert {
            it[MetricTable.type] = metricType.name
            it[MetricTable.resourceUrl] = metricCollectorInfo.resourceUrl
            it[MetricTable.authToken] = metricCollectorInfo.token
            it[MetricTable.collectorType] = metricCollectorInfo.collectorType.name
            it[MetricTable.lastSynced] = 0L
            it[MetricTable.softwareId] = softwareId
        }.let {
            convertRowToMetric(it)
        }
    }

    fun getMetrics(softwareId: Long): List<Metric> {
        return MetricTable.select(where = { MetricTable.softwareId eq softwareId }).mapNotNull {
            convertRowToMetric(it)
        }
    }

    fun getNonSyncedMetrics(bufferTime: Long): List<Metric> {
        return MetricTable.select(where = { MetricTable.lastSynced lessEq (System.currentTimeMillis() - bufferTime) })
            .orderBy(MetricTable.lastSynced to SortOrder.ASC).limit(100, 0).mapNotNull {
                convertRowToMetric(it)
            }
    }

    private fun convertRowToMetric(row: InsertStatement<Number>): Metric {
        val id = row[MetricTable.id]
        val type = row[MetricTable.type]
        val resourceUrl = row[MetricTable.resourceUrl]
        val authToken = row[MetricTable.authToken]
        val collectorType = row[MetricTable.collectorType]
        val lastSynced = row[MetricTable.lastSynced]

        return when (MetricType.valueOf(type)) {
            MetricType.REPO -> {
                RepoMetric(
                    id,
                    MetricCollectorInfo(
                        resourceUrl,
                        authToken,
                        CollectorType.valueOf(collectorType)
                    ),
                    lastSynced
                )
            }
        }
    }

    private fun convertRowToMetric(row: ResultRow): Metric {
        val id = row[MetricTable.id]
        val type = row[MetricTable.type]
        val resourceUrl = row[MetricTable.resourceUrl]
        val authToken = row[MetricTable.authToken]
        val collectorType = row[MetricTable.collectorType]
        val lastSynced = row[MetricTable.lastSynced]

        return when (MetricType.valueOf(type)) {
            MetricType.REPO -> {
                RepoMetric(
                    id,
                    MetricCollectorInfo(
                        resourceUrl,
                        authToken,
                        CollectorType.valueOf(collectorType)
                    ),
                    lastSynced
                )
            }
        }
    }
}