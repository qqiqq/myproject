package spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

class wordCountScala {
  def wordCount(inpath: String, outpath: String) = {
    val conf = new SparkConf().setAppName("Words Count")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("file://" + inpath);
    val words = rdd.flatMap(_.split(" "))
      .map(x => (x, 1))
      .reduceByKey((x, y) => x + y)

    words.saveAsTextFile("file://" + outpath)

    print(words.take(10))
  }
}

