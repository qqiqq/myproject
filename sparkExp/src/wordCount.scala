import org.apache.spark.{SparkConf,SparkContext}
import org.apache.log4j.{Level,Logger}

object wordCount {
  def main(args:Array[String]): Unit ={
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.hadoop").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("wordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(args(0))
    val words = rdd.flatMap(line => line.split(" "))
    val wordCount = words.map(w => (w,1)).reduceByKey((x,y) => x+y )
    val sortedWordCount = wordCount.map(w => (w._2,w._1)).sortByKey(false).map(w => (w._2,w._1))
    sortedWordCount.saveAsTextFile(args(1))
    sortedWordCount.take(10).foreach(println)
    sc.stop()

  }

}
