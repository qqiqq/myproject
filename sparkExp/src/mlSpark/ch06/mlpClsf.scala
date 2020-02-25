package mlSpark.ch06

import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.{DataFrame, SparkSession}

object mlpClsf {
  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().appName("MultilayerPerceptronClassifierExample").getOrCreate()

    val data : DataFrame = session.read.format("libsvm").load(args(0))

    val Array(train,test) = data.randomSplit(Array(0.8,0.1),seed = 987654)
    val layers = Array[Int](4,5,4,3)

    val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setMaxIter(100).setBlockSize(128).setSeed(1234L)

    val model = trainer.fit(train)
    val rs = model.transform(test).select("prediction","label")
    val evaluator = new MulticlassClassificationEvaluator().setPredictionCol("prediction").setLabelCol("label").setMetricName("accuracy")

    println("Test set accuracy = " + evaluator.evaluate(rs))

    session.stop()



  }

}
