package spark;

import java.util.Arrays;
import java.util.regex.Pattern;

import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;


public class wordCountJava {

    private final static Pattern pat = Pattern.compile(" ");
    private final String inpath;
    private final String outpath;


    public wordCountJava(String inpath,String outpath) {
        this.inpath = inpath;
        this.outpath = outpath;
    }

    public void count() throws Exception {
        SparkConf conf = new SparkConf().setAppName("word count");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> lines = jsc.textFile(inpath);
        JavaRDD<String> words = lines.flatMap(r -> Arrays.asList(pat.split(r)).iterator());
        JavaPairRDD<String, Integer> wordsCnt = words.mapToPair(r -> new Tuple2<String, Integer>(r, 1)).reduceByKey((x, y) -> x + y);
        wordsCnt.saveAsTextFile(outpath);
        System.out.println(wordsCnt.rdd());
    }
}
