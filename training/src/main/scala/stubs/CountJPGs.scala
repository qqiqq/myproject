package stubs

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object CountJPGs {
  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage: CountJPGs <file>")
      System.exit(1)
    }
    //val sc = new SparkContext("hdfs","weblogs")
    val sc = new SparkContext()
    //val filepath = "/loudace/weblogs/*66"
    val logfile = args(0)
    val weblogs = sc.textFile(logfile)
    val weblogsJpg = weblogs.filter(_.contains(".jpg"))
    var weblogsJpgCount = weblogsJpg.count()
    println("JPG Count : " + weblogsJpgCount)
    sc.stop
    //TODO: complete exercise
    println("stub is not implemented")
    System.exit(1)

  }
}
