import org.apache.spark.executor.TaskMetrics
import org.apache.spark.scheduler.{SparkListener, SparkListenerTaskEnd}

class PerfListener extends SparkListener {
  var totalExecutorRunTime = 0L
  var jvmGCTime = 0L
  var recordsRead = 0L
  var recordsWritten = 0L
  var resultSerializationTime = 0L

  override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit = {
    val info = taskEnd.taskInfo
    val metrics = taskEnd.taskMetrics
    updateMetricsForTask(metrics)
  }

  def updateMetricsForTask(metrics:TaskMetrics): Unit ={
    totalExecutorRunTime += metrics.executorRunTime
    jvmGCTime += metrics.jvmGCTime
    recordsRead += metrics.inputMetrics.recordsRead
    recordsWritten += metrics.outputMetrics.recordsWritten
    resultSerializationTime += metrics.resultSerializationTime
  }

}
