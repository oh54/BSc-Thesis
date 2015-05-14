package cluster.algos;

import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

import cluster.hdfs.MultipleTiffFileOF;
import cluster.hdfs.PairFunctionAdd;
import cluster.hdfs.TiffImageOutputFormat;
import cluster.hdfs.TiffImageWritable;

public class NoiseReductionMean {


	public static void main(String[] args) throws Exception {
		String appName = "SparkNoiseReductionMean";
		if (args.length < 4) {
			System.err.println("Usage: " + appName + " <inputfolder> <outputfolder> <width overlap> <height overlap>");
			System.exit(1);
		}
		// .setMaster("local")
		SparkConf conf = new SparkConf().setAppName(appName).setMaster("spark://ec2-54-82-19-63.compute-1.amazonaws.com:7077");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.addJar("/root/spark/lib/thesis2.jar");
		sc.addJar("/root/spark/lib/jai_codec.jar");
		sc.addJar("/root/spark/lib/jai_core.jar");
		sc.addJar("/root/spark/lib/jai_imageio-1.1.jar");

		String inputFolderPath = args[0];
		String outputFolderPath = args[1];
		Integer wr = Integer.parseInt(args[2]);
		Integer hr = Integer.parseInt(args[3]);
		Class<Text> keyClass = Text.class;
		Class<TiffImageWritable> valueClass = TiffImageWritable.class;

		System.out.println(inputFolderPath);
		JavaPairRDD<String, PortableDataStream> input = sc.binaryFiles(inputFolderPath, 16);

		JavaPairRDD<Text, TiffImageWritable> imgs = input.mapToPair(new PairFunctionAdd(wr, hr));



		imgs.saveAsHadoopFile(outputFolderPath, keyClass, valueClass, MultipleTiffFileOF.class);

		sc.stop();
	}



}
