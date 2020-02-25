package mlSpark.ch06

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.RegressionMetrics

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.functions._
import org.apache.spark.ml.tuning.TrainValidationSplit

object lrClsf {
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
    df.createOrReplaceTempView("stumbleUpon_preProc")

    val assembler = new VectorAssembler()
      .setInputCols(Array("avglinksize"
        ,"commonlinkratio_1"
        ,"commonlinkratio_2"
        ,"commonlinkratio_3"
        ,"commonlinkratio_4"
        ,"compression_ratio"
        ,"embed_ratio"
        ,"framebased"
        ,"frameTagRatio"
        ,"hasDomainLink"
        ,"html_ratio"
        ,"image_ratio"
        ,"is_news"
        ,"lengthyLinkDomain"
        ,"linkwordscore"
        ,"news_front_page"
        ,"non_markup_alphanum_characters"
        ,"numberOfLinks"
        ,"numwords_in_url"
        ,"parametrizedLinkRatio"
        ,"spelling_errors_ratio"))
      .setOutputCol("features")

    val lr = new LogisticRegression()
    val paramGrid = new ParamGridBuilder()
      .addGrid(lr.regParam,Array(0.1,0.01))
      .addGrid(lr.fitIntercept)
      .addGrid(lr.maxIter,Array(10,15,30))
      .addGrid(lr.elasticNetParam,Array(0.0,	0.25,	0.5,	0.75,	1.0))
      .build()
    val pipeline = new Pipeline().setStages(Array(assembler,lr))
    val trainValidationSplit = new TrainValidationSplit()
      .setEstimator(pipeline)
      .setEvaluator(new RegressionEvaluator)
      .setEstimatorParamMaps(paramGrid)
      .setTrainRatio(0.8)
    val Array(train,test) = data.randomSplit(Array(0.8,0.2),seed=137)

    val model = trainValidationSplit.fit(train)
    val holdout = model.transform(test).select("prediction","label")
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

