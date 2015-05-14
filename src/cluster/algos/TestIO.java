package cluster.algos;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;

import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;

import scala.Tuple2;
import cluster.hdfs.TiffImageOutputFormat;
import cluster.hdfs.TiffImageWritable;

public class TestIO {
	public static Integer hr;
	public static Integer wr;

	public static void main(String[] args) throws Exception {
		String appName = "SparkNoiseReductionMean";
		if (args.length < 2) {
			System.err.println("Usage: " + appName + " <inputfolder> <outputfolder>");
			System.exit(1);
		}
		// .setMaster("local")
		SparkConf conf = new SparkConf().setAppName(appName).setMaster("spark://ec2-54-82-19-63.compute-1.amazonaws.com:7077");
		JavaSparkContext sc = new JavaSparkContext(conf);

		String inputFolderPath = args[0];
		String outputFolderPath = args[1];

		Class<Text> keyClass = Text.class;
		Class<TiffImageWritable> valueClass = TiffImageWritable.class;

		JavaPairRDD<String, PortableDataStream> input = sc.binaryFiles(inputFolderPath);

		JavaPairRDD<Text, TiffImageWritable> imgs = input.mapToPair(new PairFunction<Tuple2<String, PortableDataStream>, Text, TiffImageWritable>() {
			@Override
			public Tuple2<Text, TiffImageWritable> call(Tuple2<String, PortableDataStream> t) throws Exception {
				BufferedImage bi = ImageIO.read(t._2.open());
				
				ColorModel cm = bi.getColorModel();
				boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
				WritableRaster raster = bi.copyData(null);
				
				BufferedImage n = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
				
				return new Tuple2(new Text(t._1), new TiffImageWritable(n));
				
				
			}
		});

		imgs.saveAsHadoopFile(outputFolderPath, keyClass, valueClass, TiffImageOutputFormat.class);

	}

}
