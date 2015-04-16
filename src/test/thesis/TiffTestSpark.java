package test.thesis;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public final class TiffTestSpark {

	SparkConf conf = new SparkConf().setAppName("TiffTestSpark");
	//.setMaster("local")
	JavaSparkContext sc = new JavaSparkContext(conf);
	List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
	JavaRDD<Integer> distData = sc.parallelize(data);
	
	public static void main(String[] args) throws Exception {
		

	}
}
