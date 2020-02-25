package mlSpark.ch06

import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.evaluation.RegressionMetrics

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.functions._
import org.apache.spark.ml.tuning.TrainValidationSplit

object classification {
  def main(args: Array[String]): Unit = {
    if(args.length < 1){
      println("please input train data abs path!")
    }

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
//
//    val dataPath = args(0)
//    val conf = new SparkConf().setMaster("yarn-cluster").setAppName("ml-spark classification")
//    val session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
//
//    val df = session.read.format("csv")
//      .option("delimiter","\t")
//      .option("header",true)
//      .option("inferSchema",true)
//      .load(dataPath)
//    df.createOrReplaceTempView("stumbleUpon")
//    df.printSchema()
//    session.sql("SELECT * FROM stumbleUpon WHERE alchemy_category = '?'").show()
//
//    val df1 = df.withColumn("avglinksize",df("avglinksize").cast("double"))
//      .withColumn("commonlinkratio_1",df("commonlinkratio_1").cast("double"))
//      .withColumn("commonlinkratio_2",df("commonlinkratio_2").cast("double"))
//      .withColumn("commonlinkratio_3",df("commonlinkratio_3").cast("double"))
//      .withColumn("commonlinkratio_4",df("commonlinkratio_4").cast("double"))
//      .withColumn("compression_ratio",df("compression_ratio").cast("double"))
//      .withColumn("embed_ratio",df("embed_ratio").cast("double"))
//      .withColumn("framebased",df("framebased").cast("double"))
//      .withColumn("frameTagRatio",df("frameTagRatio").cast("double"))
//      .withColumn("hasDomainLink",df("hasDomainLink").cast("double"))
//      .withColumn("html_ratio",df("html_ratio").cast("double"))
//      .withColumn("image_ratio",df("image_ratio").cast("double"))
//      .withColumn("is_news",df("is_news").cast("double"))
//      .withColumn("lengthyLinkDomain",df("lengthyLinkDomain").cast("double"))
//      .withColumn("linkwordscore",df("linkwordscore").cast("double"))
//      .withColumn("news_front_page",df("news_front_page").cast("double"))
//      .withColumn("non_markup_alphanum_characters",df("non_markup_alphanum_characters").cast("double"))
//      .withColumn("numberOfLinks",df("numberOfLinks").cast("double"))
//      .withColumn("numwords_in_url",df("numwords_in_url").cast("double"))
//      .withColumn("parametrizedLinkRatio",df("parametrizedLinkRatio").cast("double"))
//      .withColumn("spelling_errors_ratio",df("spelling_errors_ratio").cast("double"))
//      .withColumn("label",df("label").cast("double"))
//
//    df1.printSchema()
//    val replQuesMarkFunc = udf{(x:Double) => if(x == "?") 0.0 else x}
////    for naive bayes classification
////    val replNegFeatFunc = udf{(x:Double) => if(x < 0) 0.0 else x}
//
//    val df2 = df1
//      .withColumn("avglinksize",replQuesMarkFunc(df1("avglinksize")))
//      .withColumn("commonlinkratio_1",replQuesMarkFunc(df1("commonlinkratio_1")))
//      .withColumn("commonlinkratio_2",replQuesMarkFunc(df1("commonlinkratio_2")))
//      .withColumn("commonlinkratio_3",replQuesMarkFunc(df1("commonlinkratio_3")))
//      .withColumn("commonlinkratio_4",replQuesMarkFunc(df1("commonlinkratio_4")))
//      .withColumn("compression_ratio",replQuesMarkFunc(df1("compression_ratio")))
//      .withColumn("embed_ratio",replQuesMarkFunc(df1("embed_ratio")))
//      .withColumn("framebased",replQuesMarkFunc(df1("framebased")))
//      .withColumn("frameTagRatio",replQuesMarkFunc(df1("frameTagRatio")))
//      .withColumn("hasDomainLink",replQuesMarkFunc(df1("hasDomainLink")))
//      .withColumn("html_ratio",replQuesMarkFunc(df1("html_ratio")))
//      .withColumn("image_ratio",replQuesMarkFunc(df1("image_ratio")))
//      .withColumn("is_news",replQuesMarkFunc(df1("is_news")))
//      .withColumn("lengthyLinkDomain",replQuesMarkFunc(df1("lengthyLinkDomain")))
//      .withColumn("linkwordscore",replQuesMarkFunc(df1("linkwordscore")))
//      .withColumn("news_front_page",replQuesMarkFunc(df1("news_front_page")))
//      .withColumn("non_markup_alphanum_characters",replQuesMarkFunc(df1("non_markup_alphanum_characters")))
//      .withColumn("numberOfLinks",replQuesMarkFunc(df1("numberOfLinks")))
//      .withColumn("numwords_in_url",replQuesMarkFunc(df1("numwords_in_url")))
//      .withColumn("parametrizedLinkRatio",replQuesMarkFunc(df1("parametrizedLinkRatio")))
//      .withColumn("spelling_errors_ratio",replQuesMarkFunc(df1("spelling_errors_ratio")))
//      .withColumn("label",replQuesMarkFunc(df1("label")))
//
//    val df3 = df2.drop("url").drop("urlid").drop("boilerplate").drop("alchemy_category").drop("alchemy_category_score")
//    val df4 = df3.na.fill(0.0)
    data.createOrReplaceTempView("stumbleUpon_preProc")

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
