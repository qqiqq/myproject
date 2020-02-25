package mlSpark.ch06

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
import org.apache.spark.ml.{Pipeline, PipelineStage}

import scala.collection.mutable

object dtClsf {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("please input train data abs path!")
      sys.exit(1)
    }

    val logger = Logger.getLogger(getClass.getName)

    val dataPath = args(0)
    val conf = new SparkConf().setMaster("yarn-cluster").setAppName("ml-spark LogisticRegression classification")
    val session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()

    val df = session.read.format("csv")
      .option("delimiter", "\t")
      .option("header", true)
      .option("inferSchema", true)
      .load(dataPath)
    df.createOrReplaceTempView("stumbleUpon")
    df.printSchema()
    session.sql("SELECT * FROM stumbleUpon WHERE alchemy_category = '?'").show()

    val df1 = df.withColumn("avglinksize", df("avglinksize").cast("double"))
      .withColumn("commonlinkratio_1", df("commonlinkratio_1").cast("double"))
      .withColumn("commonlinkratio_2", df("commonlinkratio_2").cast("double"))
      .withColumn("commonlinkratio_3", df("commonlinkratio_3").cast("double"))
      .withColumn("commonlinkratio_4", df("commonlinkratio_4").cast("double"))
      .withColumn("compression_ratio", df("compression_ratio").cast("double"))
      .withColumn("embed_ratio", df("embed_ratio").cast("double"))
      .withColumn("framebased", df("framebased").cast("double"))
      .withColumn("frameTagRatio", df("frameTagRatio").cast("double"))
      .withColumn("hasDomainLink", df("hasDomainLink").cast("double"))
      .withColumn("html_ratio", df("html_ratio").cast("double"))
      .withColumn("image_ratio", df("image_ratio").cast("double"))
      .withColumn("is_news", df("is_news").cast("double"))
      .withColumn("lengthyLinkDomain", df("lengthyLinkDomain").cast("double"))
      .withColumn("linkwordscore", df("linkwordscore").cast("double"))
      .withColumn("news_front_page", df("news_front_page").cast("double"))
      .withColumn("non_markup_alphanum_characters", df("non_markup_alphanum_characters").cast("double"))
      .withColumn("numberOfLinks", df("numberOfLinks").cast("double"))
      .withColumn("numwords_in_url", df("numwords_in_url").cast("double"))
      .withColumn("parametrizedLinkRatio", df("parametrizedLinkRatio").cast("double"))
      .withColumn("spelling_errors_ratio", df("spelling_errors_ratio").cast("double"))
      .withColumn("label", df("label").cast("double"))

    df1.printSchema()
    val replQuesMarkFunc = udf { (x: Double) => if (x == "?") 0.0 else x }
    //    for naive bayes classification
    val replNegFeatFunc = udf{(x:Double) => if(x < 0) 0.0 else x}
    val df2 = df1
      .withColumn("avglinksize",replNegFeatFunc(replQuesMarkFunc(df1("avglinksize"))))
      .withColumn("commonlinkratio_1",replNegFeatFunc(replQuesMarkFunc(df1("commonlinkratio_1"))))
      .withColumn("commonlinkratio_2",replNegFeatFunc(replQuesMarkFunc(df1("commonlinkratio_2"))))
      .withColumn("commonlinkratio_3",replNegFeatFunc(replQuesMarkFunc(df1("commonlinkratio_3"))))
      .withColumn("commonlinkratio_4",replNegFeatFunc(replQuesMarkFunc(df1("commonlinkratio_4"))))
      .withColumn("compression_ratio",replNegFeatFunc(replQuesMarkFunc(df1("compression_ratio"))))
      .withColumn("embed_ratio",replNegFeatFunc(replQuesMarkFunc(df1("embed_ratio"))))
      .withColumn("framebased",replNegFeatFunc(replQuesMarkFunc(df1("framebased"))))
      .withColumn("frameTagRatio",replNegFeatFunc(replQuesMarkFunc(df1("frameTagRatio"))))
      .withColumn("hasDomainLink",replNegFeatFunc(replQuesMarkFunc(df1("hasDomainLink"))))
      .withColumn("html_ratio",replNegFeatFunc(replQuesMarkFunc(df1("html_ratio"))))
      .withColumn("image_ratio",replNegFeatFunc(replQuesMarkFunc(df1("image_ratio"))))
      .withColumn("is_news",replNegFeatFunc(replQuesMarkFunc(df1("is_news"))))
      .withColumn("lengthyLinkDomain",replNegFeatFunc(replQuesMarkFunc(df1("lengthyLinkDomain"))))
      .withColumn("linkwordscore",replNegFeatFunc(replQuesMarkFunc(df1("linkwordscore"))))
      .withColumn("news_front_page",replNegFeatFunc(replQuesMarkFunc(df1("news_front_page"))))
      .withColumn("non_markup_alphanum_characters",replNegFeatFunc(replQuesMarkFunc(df1("non_markup_alphanum_characters"))))
      .withColumn("numberOfLinks",replNegFeatFunc(replQuesMarkFunc(df1("numberOfLinks"))))
      .withColumn("numwords_in_url",replNegFeatFunc(replQuesMarkFunc(df1("numwords_in_url"))))
      .withColumn("parametrizedLinkRatio",replNegFeatFunc(replQuesMarkFunc(df1("parametrizedLinkRatio"))))
      .withColumn("spelling_errors_ratio",replNegFeatFunc(replQuesMarkFunc(df1("spelling_errors_ratio"))))
      .withColumn("label",replNegFeatFunc(replQuesMarkFunc(df1("label"))))

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
    val dt = new DecisionTreeClassifier()
        .setFeaturesCol(assembler.getOutputCol)
        .setLabelCol("indexedLabel")
        .setMaxDepth(5)
        .setMaxBins(32)
        .setMinInstancesPerNode(1)
        .setMinInfoGain(0.0)
        .setCacheNodeIds(false)
        .setCheckpointInterval(10)

    stages += assembler
    stages += dt
    val pipeline = new Pipeline().setStages(stages.toArray)
    val startTime = System.nanoTime()
    val model = pipeline.fit(train)
    val elpasedTime = (System.nanoTime() - startTime) / 1e9
    println(s"Training time: $elpasedTime seconds" )

    val holdout = model.transform(test).select("prediction","label")

    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")

    val accuracy = evaluator.evaluate(holdout)
    println("Test set accuracy = " + accuracy)

    session.stop()

  }



}
