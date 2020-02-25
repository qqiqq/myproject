import org.apache.log4j.{Level,Logger}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.SparkSession

object SaleDataDeal {
  def main(args: Array[String]): Unit ={
    if (args.length != 3){
      println("Usage: /path/to/spark/bin/spark-submit --master yarn --mode cluster --driver-memory 1g"
        + " --class test.SaleDataDeal test.jar " )
      sys.exit(1)
    }
    Logger.getLogger("org.apache.hadoop").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val spark = SparkSession.builder().enableHiveSupport()
      .appName(s"${this.getClass.getSimpleName}")
      .config("hive.metastore.uris","thrift://192.168.159.15:9083")
      .enableHiveSupport()
      .getOrCreate()

    spark.sql("SET spark.sql.shuffle.partitions=20")
///***    val db = spark.sql("show databases")
//    val rddDB = db.rdd.map(l => l(0))
//    rddDB.collect().foreach(println)
//    rddDB.saveAsTextFile(args(3))
//***/
    spark.sql("USE HIVE")
    val sqldata = spark.sql("" +
      "select a.locationid," +
      "       sum(b.qty) totalqty," +
      "       sum(b.amount) totalamount" +
      "  from tbstock a " +
      "  join tbstockdetail b " +
      "  on a.ordernumber=b.ordernumber " +
      "  group by a.locationid").cache()

    val vectors = sqldata.rdd.map(d=> Vectors.dense(d.getLong(1).toDouble,d.getLong(2).toDouble))
    val numClusters = args(0).toInt
    val numIterations = args(1).toInt
    val model = KMeans.train(vectors,numClusters,numIterations)

    println("Cluster centers: ")
    model.clusterCenters.foreach(println)

    val rs = sqldata.rdd.map{
      line =>
        val lineVectore = Vectors.dense(line.getLong(1).toDouble,line.getLong(2).toDouble)
        val prediction = model.predict(lineVectore)
        line(0) + " " + line.getLong(1) + " " + line.getLong(2) + " " + prediction
    }
    println("Prediction detail:")
    rs.collect().foreach(println)
    rs.saveAsTextFile(args(2))

  }
}
