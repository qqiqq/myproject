package mlSpark.ch06

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object rndForest {
  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)

    val dataPath = args(0)
    val conf = new SparkConf().setMaster("yarn-cluster").setAppName("ml-spark classification")
    val session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()

    val df = session.read.format("csv")
      .option("delimiter", "\t")
      .option("header", true)
      .option("inferSchema", true)
      .load(dataPath)

    val data = featureExt.load(df)
    val Array(train,test) = data.randomSplit(Array(0.8,0.1),seed = 123456)
    val stages = new mutable.ArrayBuffer[PipelineStage]()
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedlabel")
    val assembler = featureExt.featVectAssembler()
    stages += labelIndexer
    val rf = new RandomForestClassifier().setFeaturesCol(assembler.getOutputCol).setLabelCol("indexedlabel").setNumTrees(20).setMaxBins(32).setMaxDepth(10).setMinInstancesPerNode(1).setCacheNodeIds(false).setCheckpointInterval(10)
    stages += assembler
    stages += rf
    val pipeline = new Pipeline().setStages(stages.toArray)
    val startTime = System.nanoTime()
    val model = pipeline.fit(train)
    val elapsedTime = (System.nanoTime() - startTime)/1e9
    println(s"Training	time:	$elapsedTime	seconds")

    val holdout = model.transform(test).select("label","prediction")
    val evaluator = new MulticlassClassificationEvaluator().setPredictionCol("prediction").setLabelCol("label").setMetricName("accuracy")
    val accuracy = evaluator.evaluate(holdout)
    println("Test	set	accuracy	=	"	+	accuracy)

  }

}
