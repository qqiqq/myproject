package mlSpark.ch06

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.SVMWithSGD

object svmClsf {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("please input train data abs path!")
    }
    val dataPath = args(0)
    val conf = new SparkConf().setMaster("yarn-cluster").setAppName("ml-spark LogisticRegression classification")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(dataPath).map(r => r.split("\t"))
    val data = rdd.map{ r =>
      val trimmed = r.map(_.replaceAll("\"",""))
      val label = trimmed(r.size - 1).toDouble
      val feature = trimmed.slice(4,r.size - 1).map(d => if (d == "?") 0.0 else d.toDouble)
      LabeledPoint(label,Vectors.dense(feature))
    }

    val Array(train,test) = data.randomSplit(Array(0.8,0.2))
    val numIter = 10
    val svmModel =  SVMWithSGD.train(train,numIter)

    svmModel.clearThreshold()
    val svmTotCorrect = test.map{ d =>
      if(svmModel.predict(d.features) == d.label) 1 else 0
    }.sum()

    print(svmTotCorrect/test.count())

    sc.stop()

  }

}
