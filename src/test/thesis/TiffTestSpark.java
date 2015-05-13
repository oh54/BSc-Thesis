package test.thesis;

import javax.imageio.ImageIO;

import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

import cluster.hdfs.TiffImageWritable;
import cluster.hdfs.TiffImageOutputFormat;
import scala.Tuple2;

public class TiffTestSpark {

	public static void main(String[] args) throws Exception {

		String appName = "TiffTestSpark";

		if (args.length < 1) {
			System.err.println("Usage: " + appName + " <folder>");
			System.exit(1);
		}

		SparkConf conf = new SparkConf().setAppName(appName).setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		String inputFolder = args[0];

		Class<TiffImageInputFormat> format = TiffImageInputFormat.class;
		Class<Text> key = Text.class;
		Class<TiffImageWritable> value = TiffImageWritable.class;

		/*
		 * JavaPairRDD<Text, ImageWritable> imgs = sc.hadoopFile(inputFolder,
		 * format, key, value);
		 */

		// FileSystem localfs = FileSystem.getLocal(conf);
		// FileSystem hdfs = FileSystem.get(conf);

		// sc.hadoopFile(path, inputFormatClass, keyClass, valueClass);

		// sc.hadoopFile(path, inputFormatClass, keyClass, valueClass);

		/*
		JavaPairRDD<String, PortableDataStream> input = sc.binaryFiles(inputFolder);
		 JavaRDD<String> paths = input.map(tuple -> new String(tuple._1));


		 JavaPairRDD<Text, TiffImageWritable> imgs = input.mapToPair(t -> {
			 return new Tuple2(new Text(t._1), new TiffImageWritable(ImageIO.read(t._2.open()))); 
		 });
		 
		 */
	
		/*
		 * JavaPairRDD<String, Integer> heights = imgs.mapToPair(t -> new
		 * Tuple2( t._1, t._2.getHeight()));
		 */
		 /*
		JavaRDD<String> strings = imgs.map(t -> t._2.getStr());
		List<String> stringsList = strings.collect();
		for (String str : stringsList) {
			System.out.println(str);
		}
		System.out.println(strings.count());
		*/
		 /*
		JavaRDD<Integer> heights = imgs.map(t -> t._2.getHeight());

		List<Integer> heghtsList = heights.collect();
		for (Integer height : heghtsList) {
			System.out.println(height);
		}

		*/
		String path = "hdfs://localhost:9000/user/oskar/output";
		//imgs.saveAsHadoopFile(path, Text.class, ImageWritable.class, TiffImageOutputFormat.class);

		//imgs.saveAsHadoopFile(path, Text.class, ImageWritable.class, TiffImageOutputFormat.class);õ

		
		//imgs.saveAsHadoopFile(path, key, value, TiffImageOutputFormat.class);
		
		//imgs.saveAsNewAPIHadoopFile(path, Text.class, IntWritable.class,  SequenceFileOutputFormat.class);
	
		/*
		 * List<String> pathsList = paths.collect(); for (String path :
		 * pathsList) { System.out.println(path); }
		 */
		// BufferedImage tiffImg = ImageIO.read(new
		// File("input/sar/sarpic.tif"));

		sc.stop();

	}
}
