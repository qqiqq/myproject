import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object showDB {
  def main(args: Array[String]): Unit ={

    Logger.getLogger("org.apache.hadoop").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val spark = SparkSession.builder().enableHiveSupport()
      .appName(s"${this.getClass.getSimpleName}")
      .getOrCreate()

    spark.sql("SET spark.sql.shuffle.partitions=20")
    val db = spark.sql("show databases")
    db.collect().foreach(println)
    val rs = db.rdd.map{
      line =>
        if(line(0) == "hive"){
          spark.sql("use hive")
          spark.sql("show tables").collect().foreach(println)
        }
    }



  }

}
