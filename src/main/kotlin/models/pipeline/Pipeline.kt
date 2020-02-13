package models.pipeline

class Pipeline() {

    private val jobs : MutableSet<PipelineJob> = mutableSetOf()

    fun addJob(job : PipelineJob) {
        jobs.add(job)
    }

    fun getJobs() = jobs.toList()

    fun isFailed() : Boolean {
        jobs.forEach { job ->
            if(job.failed) {
                return false
            }
        }

        return true
    }
}