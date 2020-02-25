package mlSpark.ch06

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
import org.apache.spark.ml.{Pipeline, PipelineStage}

import scala.collection.mutable

object nbClsf {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
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

    val df1 = featureExt.load(df)

    //    for naive bayes classification
    val replNegFeatFunc = udf{(x:Double) => if(x < 0) 0.0 else x}
    val df2 = df1
      .withColumn("avglinksize",replNegFeatFunc(df1("avglinksize")))
      .withColumn("commonlinkratio_1",replNegFeatFunc(df1("commonlinkratio_1")))
      .withColumn("commonlinkratio_2",replNegFeatFunc(df1("commonlinkratio_2")))
      .withColumn("commonlinkratio_3",replNegFeatFunc(df1("commonlinkratio_3")))
      .withColumn("commonlinkratio_4",replNegFeatFunc(df1("commonlinkratio_4")))
      .withColumn("compression_ratio",replNegFeatFunc(df1("compression_ratio")))
      .withColumn("embed_ratio",replNegFeatFunc(df1("embed_ratio")))
      .withColumn("framebased",replNegFeatFunc(df1("framebased")))
      .withColumn("frameTagRatio",replNegFeatFunc(df1("frameTagRatio")))
      .withColumn("hasDomainLink",replNegFeatFunc(df1("hasDomainLink")))
      .withColumn("html_ratio",replNegFeatFunc(df1("html_ratio")))
      .withColumn("image_ratio",replNegFeatFunc(df1("image_ratio")))
      .withColumn("is_news",replNegFeatFunc(df1("is_news")))
      .withColumn("lengthyLinkDomain",replNegFeatFunc(df1("lengthyLinkDomain")))
      .withColumn("linkwordscore",replNegFeatFunc(df1("linkwordscore")))
      .withColumn("news_front_page",replNegFeatFunc(df1("news_front_page")))
      .withColumn("non_markup_alphanum_characters",replNegFeatFunc(df1("non_markup_alphanum_characters")))
      .withColumn("numberOfLinks",replNegFeatFunc(df1("numberOfLinks")))
      .withColumn("numwords_in_url",replNegFeatFunc(df1("numwords_in_url")))
      .withColumn("parametrizedLinkRatio",replNegFeatFunc(df1("parametrizedLinkRatio")))
      .withColumn("spelling_errors_ratio",replNegFeatFunc(df1("spelling_errors_ratio")))
      .withColumn("label",replNegFeatFunc(df1("label")))

    val df3 = df2.drop("url").drop("urlid").drop("boilerplate").drop("alchemy_category").drop("alchemy_category_score")
    val df4 = df3.na.fill(0.0)
    df4.createOrReplaceTempView("stumbleUpon_preProc")

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

    val Array(train,test) = df4.randomSplit(Array(0.8,0.2),seed=137)
    val stages = new mutable.ArrayBuffer[PipelineStage]()
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel")
    stages += labelIndexer
    val nb = new NaiveBayes()
    stages += assembler
    stages += nb
    val pipeline = new Pipeline().setStages(stages.toArray)
    val startTime = System.nanoTime()
    val model = pipeline.fit(train)
    val elpasedTime = (System.nanoTime() - startTime) / 1e9
    println(s"Training time: $elpasedTime seconds" )

    val holdout = model.transform(test).select("prediction","label")

    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")

    val accuracy = evaluator.evaluate(holdout)
    println("Test set accuracy = " + accuracy)

    }



}
