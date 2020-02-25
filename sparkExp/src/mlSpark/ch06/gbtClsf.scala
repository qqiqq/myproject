package mlSpark.ch06

import org.apache.spark.SparkConf
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.classification.GBTClassifier
import org.apache.spark.ml.evaluation.{MulticlassClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.spark.mllib.evaluation.RegressionMetrics

import scala.collection.mutable

object gbtClsf {
  def main(args: Array[String]): Unit = {
    if (args.length<1){
      println("please input train data abs path!")
    }

    val logger = Logger.getLogger(getClass)

    val datapath = args(0)
    val conf = new SparkConf().setAppName("GBT classifictaion").setMaster("yarn-cluster")
    val session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
    val df = session.read.format("csv").option("header",true).option("delimiter","\t").option("inferSchema",true).load(datapath)
    val data = featureExt.load(df)

    val assembler = featureExt.featVectAssembler()
    val Array(train,test) = data.randomSplit(Array(0.8,0.2),seed = 123456)
    val stages = new mutable.ArrayBuffer[PipelineStage]()
    val labelIndexer = featureExt.labelExt()
    stages += labelIndexer
    val gbt = new GBTClassifier().setFeaturesCol(assembler.getOutputCol).setLabelCol("indexedLabel").setMaxIter(10)
    stages += assembler
    stages += gbt
    val pipeline = new Pipeline().setStages(stages.toArray)

    val startTime = System.nanoTime()
    val model = pipeline.fit(train)
    val elapsedTime = (System.nanoTime() - startTime) / 1e9
    println(s"Training	time:	$elapsedTime	seconds")

    val holdout = model.transform(test).select("label","prediction")

    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
    val accuracy = evaluator.evaluate(holdout)

    logger.info("Test accuracy: ")
    logger.info(accuracy)

    val rm = new RegressionMetrics(holdout.rdd.map(x => (x(0).asInstanceOf[Double],x(1).asInstanceOf[Double])))

    logger.info("Test	Metrics")
    logger.info("Test	Explained	Variance:")
    logger.info(rm.explainedVariance)
    logger.info("Test	R^2	Coef:")
    logger.info(rm.r2)
    logger.info("Test	MSE:")
    logger.info(rm.meanSquaredError)
    logger.info("Test	RMSE:")
    logger.info(rm.rootMeanSquaredError)

  }

}
