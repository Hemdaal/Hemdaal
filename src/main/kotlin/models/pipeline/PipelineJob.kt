package models.pipeline

abstract class PipelineJob(
    val failed : Boolean,
    val isTriggered: Boolean,
    val type: PipelineJobType
) {

}