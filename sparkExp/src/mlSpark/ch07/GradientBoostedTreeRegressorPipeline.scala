package mlSpark.ch07

import org.apache.log4j.Logger
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, VectorIndexer}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.ml.regression.{GBTRegressionModel, GBTRegressor}

object GradientBoostedTreeRegressorPipeline {
  @transient lazy val logger = Logger.getLogger(getClass.getName)

  def gbtRegressionWithVectorFormat(vectorAssembler:VectorAssembler, vectorIndexer:VectorIndexer, dataFrame:DataFrame): Unit = {
    val gr = new GBTRegressor()
      .setPredictionCol("prediction")
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setMaxIter(10)
      .setMaxDepth(10)
      .setSeed(12345)

    val stage = Array(vectorAssembler, vectorIndexer, gr)
    val pipeline = new Pipeline().setStages(stage)
    val Array(train, test) = dataFrame.randomSplit(Array(0.8, 0.2), seed = 12345)

    val model = pipeline.fit(train)

    val predictions = model.transform(test).cache()
    predictions.select("label","prediction","features").show(5)
    val evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    val	treeModel	=	model.stages(1).asInstanceOf[GBTRegressionModel]
    println("Learned	regression	tree	model:\n"	+
      treeModel.toDebugString)

  }


  def gbtRegressionWithSVMFormat(spark:SparkSession,path:String): Unit ={
    val df = spark.read.format("libsvm").load(path)
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(df)
    val Array(train,test) = df.randomSplit(Array(0.8,0.2), seed = 12345)

    val gr = new GBTRegressor()
      .setPredictionCol("prediction")
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setMaxIter(10)
      .setMaxDepth(10)
      .setSeed(12345)


    val pipeline = new Pipeline().setStages(Array(featureIndexer, gr))

    val model = pipeline.fit(train)

    val predictions = model.transform(test).cache()
    predictions.select("label","prediction","features").show(5)
    val evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    val	treeModel	=	model.stages(1).asInstanceOf[GBTRegressionModel]
    println("Learned	regression	tree	model:\n"	+
      treeModel.toDebugString)

  }
}
