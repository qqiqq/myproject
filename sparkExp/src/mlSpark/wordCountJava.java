package mlSpark;
import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.regex.Pattern;
import java.util.Arrays;


public final class wordCountJava {
    private final static Pattern pat = Pattern.compile(" ");

    public static void main(String[] args) throws Exception{
        JavaSparkContext jsc = new JavaSparkContext();
        JavaRDD<String> lines = jsc.textFile(args[0]);
        JavaRDD<String> words = lines.flatMap(r -> Arrays.asList(pat.split(r)).iterator());
        JavaPairRDD<String,Integer> wordsCnt = words.mapToPair(r -> new Tuple2<String,Integer>(r,1)).reduceByKey((x,y) -> x+y);
        System.out.println(wordsCnt);
    }
}
