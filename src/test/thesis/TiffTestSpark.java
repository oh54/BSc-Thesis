package test.thesis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

import scala.Tuple2;

public class TiffTestSpark {

	public static <R> void main(String[] args) throws Exception {

		String appName = "TiffTestSpark";

		if (args.length < 1) {
			System.err.println("Usage: " + appName + " <folder>");
			System.exit(1);
		}

		SparkConf conf = new SparkConf().setAppName(appName).setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		String inputFolder = args[0];

		// FileSystem localfs = FileSystem.getLocal(conf);
		// FileSystem hdfs = FileSystem.get(conf);

		// sc.hadoopFile(path, inputFormatClass, keyClass, valueClass);

		JavaPairRDD<String, PortableDataStream> input = sc
				.binaryFiles(inputFolder);
		//JavaRDD<String> paths = input.map(tuple -> new String(tuple._1.substring(7)));

		JavaPairRDD<String, BufferedImage> imgs = input.mapToPair(t -> {
			return new Tuple2(t._1, ImageIO.read(t._2.open()));
		});

		//JavaPairRDD<String, Integer> heights = imgs.mapToPair(t -> new Tuple2(
		//		t._1, t._2.getHeight()));

		
		JavaRDD<Integer> heights = imgs.map(t -> t._2.getHeight());
		List<Integer> heghtsList = heights.collect();
		for (Integer height : heghtsList){
			System.out.println(height);
		}
		

		/*
		List<String> pathsList = paths.collect();
		for (String path : pathsList) {
			System.out.println(path);
		}
		*/
		// BufferedImage tiffImg = ImageIO.read(new
		// File("input/sar/sarpic.tif"));

		sc.stop();

	}
}
