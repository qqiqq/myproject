package mlSpark.ch07

import org.apache.log4j.Logger
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, VectorIndexer}
import org.apache.spark.ml.regression.{RandomForestRegressionModel, RandomForestRegressor}
import org.apache.spark.sql.{DataFrame, SparkSession}


object RandomForestRegressionPipeline {
  @transient lazy val logger = Logger.getLogger(getClass.getName)

  def randForestRegressionWithVectorFormat(vectorAssembler: VectorAssembler,vectorIndexer: VectorIndexer,dataFrame: DataFrame): Unit ={
    val rf = new RandomForestRegressor()
      .setFeaturesCol("features")
      .setLabelCol("label")
      .setMaxDepth(10)
      .setNumTrees(5)
      .setSeed(12345)

    val pipeline = new Pipeline().setStages(Array(vectorAssembler,vectorIndexer,rf))
    val Array(train,test) = dataFrame.randomSplit(Array(0.8,0.2),seed=54321)

    val model = pipeline.fit(train)

    val predictions = model.transform(test).cache()
    predictions.select("prediction","label","features").show(5)
    val evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    println("Root	Mean	Squared	Error	(RMSE)	on	test	data	=	"	+	rmse)
    val treeModel = model.stages(1).asInstanceOf[RandomForestRegressionModel]
    println("Learned	regression	tree	model:\n"	+	treeModel.toDebugString)
  }

  def randForestRegressionWithSVMFormat(spark:SparkSession,path:String): Unit ={
    val df = spark.read.format("libsvm").load(path)
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(df)
    val Array(train,test) = df.randomSplit(Array(0.8,0.2))
    val rf = new RandomForestRegressor()
      .setLabelCol("label")
      .setFeaturesCol("indexedFeatures")
      .setPredictionCol("prediction")
      .setSeed(12345)
      .setMaxDepth(10)
      .setNumTrees(5)
    val pipeline = new Pipeline().setStages(Array(featureIndexer,rf))
    val model = pipeline.fit(train)

    val predictions = model.transform(test).cache()
    predictions.select("prediction","label","features").show(5)
    val evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    println("Root	Mean	Squared	Error	(RMSE)	on	test	data	=	"	+	rmse)
    val treeModel = model.stages(1).asInstanceOf[RandomForestRegressionModel]
    println("Learned	regression	tree	model:\n"	+	treeModel.toDebugString)


  }

}
