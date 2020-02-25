import org.apache.spark.sql.types._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._

import org.apache.log4j.{Level, Logger}
import org.graphframes._
import org.apache.spark.graphx._

object graphSCC {
  def main(args:Array[String]): Unit ={
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.hadoop").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .appName(s"${this.getClass.getSimpleName}")
      .getOrCreate()

    val edgesRDD = spark.sparkContext.textFile("hdfs://prod/spark/ch7/amazon0601.txt")
    val schemaString = "src dst"
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = false))
    val edgesSchema = new StructType(fields)
    val rowRDD = edgesRDD.filter(x => !x.contains("#")).map(_.split("\t")).map(attributes => Row(attributes(0).trim, attributes(1).trim))
    val edgesDF = spark.createDataFrame(rowRDD, edgesSchema)
    val srcVerticesDF = edgesDF.select("src").distinct
    val destVerticesDF = edgesDF.select("dst").distinct
    val verticesDF = srcVerticesDF.union(destVerticesDF).distinct().select("src").withColumnRenamed("src","id")
    val g = GraphFrame(verticesDF, edgesDF)
    val para = args(0)
    val v1 = g.vertices.filter("id < " + para)
    val e1 = g.edges.filter("src < " + para)
    val g2 = GraphFrame(v1.repartition(4,v1("id")),e1.repartition(4,e1("src")))

    val result = g2.connectedComponents.setAlgorithm("graphx").run()
    result.select("id", "component").groupBy("component").count().sort(desc("count")).show()
    spark.stop()

  }

}
