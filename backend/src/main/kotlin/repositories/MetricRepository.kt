package repositories

import db.MetricTable
import domains.metric.CollectorType
import domains.metric.Metric
import domains.metric.MetricCollectorData
import domains.metric.MetricType
import domains.metric.repo.RepoMetric
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class MetricRepository {

    fun addMetric(metricType: MetricType, metricCollectorData: MetricCollectorData, softwareId: Long): Metric {
        return MetricTable.insert {
            it[MetricTable.type] = metricType.name
            it[MetricTable.resourceUrl] = metricCollectorData.resourceUrl
            it[MetricTable.authToken] = metricCollectorData.token
            it[MetricTable.collectorType] = metricCollectorData.collectorType.name
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

    fun getLastSync(metricId: Long): Long {
        return MetricTable.select(where = { MetricTable.id eq metricId }).singleOrNull()?.let {
            it[MetricTable.lastSynced]
        } ?: 0
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

        return when (MetricType.valueOf(type)) {
            MetricType.REPO -> {
                RepoMetric(
                    id,
                    MetricCollectorData(
                        resourceUrl,
                        authToken,
                        CollectorType.valueOf(collectorType)
                    )
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
                    MetricCollectorData(
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