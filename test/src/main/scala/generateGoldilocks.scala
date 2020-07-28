import org.apache.spark.mllib.random.RandomRDDs
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD


object generateGoldilocks {
  def main(args: Array[String]): Unit = {
    case class GoldiLocksRow(pandaId : Double, softness : Double, fuzzyness : Double, size : Double)
    val sc = new SparkContext(master="local[4]",appName = "test", new SparkConf)


    val zipRDD = RandomRDDs.exponentialRDD(sc, 1000, 100).map(_.toInt.toString)
    val valuesRDD = RandomRDDs.normalVectorRDD(sc,1000,100)
    print(zipRDD)
    print(valuesRDD)
//    zipRDD.zip(valuesRDD).map{case(z,v) =>
//    GoldiLocksRow(1,z,v)}

  }


}
