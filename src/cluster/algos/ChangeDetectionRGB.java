package cluster.algos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

import scala.Tuple2;
import cluster.hdfs.PngImageOutputFormat;
import cluster.hdfs.PngImageWritable;
import cluster.hdfs.TiffImageOutputFormat;
import cluster.hdfs.TiffImageWritable;

public class ChangeDetectionRGB {

	public static void main(String[] args) throws Exception {
		String appName = "SparkChangeDetectionRGB";
		if (args.length < 3) {
			System.err.println("Usage: " + appName + " <inputfolder1> <inputfolder1> <outputfolder>");
			System.exit(1);
		}
		// .setMaster("local")
		SparkConf conf = new SparkConf().setAppName(appName);
		JavaSparkContext sc = new JavaSparkContext(conf);

		String inputFolderPath1 = args[0];
		String inputFolderPath2 = args[1];
		String outputFolderPath = args[2];

		JavaPairRDD<String, PortableDataStream> files1 = sc.binaryFiles(inputFolderPath1, 16);

		JavaPairRDD<String, PortableDataStream> files2 = sc.binaryFiles(inputFolderPath2, 16);

		JavaPairRDD<String, PortableDataStream> data1 = files1.mapToPair(t -> {
			String[] tokens = t._1.split("/");;
			String fileName = tokens[tokens.length - 1];
			//BufferedImage origImg = ImageIO.read(t._2.open());
			return new Tuple2(fileName, t._2);
		});
		
		JavaPairRDD<String, PortableDataStream> data2 = files2.mapToPair(t -> {
			String[] tokens = t._1.split("/");;
			String fileName = tokens[tokens.length - 1];
			//BufferedImage origImg = ImageIO.read(t._2.open());
			return new Tuple2(fileName, t._2);
		});
				
		JavaPairRDD<String, Tuple2<PortableDataStream, PortableDataStream>> joinedData = data1.join(data2);

		JavaPairRDD<Text, PngImageWritable> proccessedImgs = joinedData.mapToPair(t -> {
			BufferedImage img1 = ImageIO.read(t._2._1.open());
			BufferedImage img2 = ImageIO.read(t._2._2.open());
			
			BufferedImage processedImg = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < processedImg.getWidth(); x++) {
				for (int y = 0; y < processedImg.getHeight(); y++) {
					int r1 = (new Color(img1.getRGB(x, y))).getRed();
					int g2 = (new Color(img2.getRGB(x, y))).getGreen();
					processedImg.setRGB(x, y, (new Color(r1, g2, (r1 + g2) / 2).getRGB()));
				}
			}
			
			return new Tuple2(new Text(t._1), new PngImageWritable(processedImg));
		});
		
		proccessedImgs.saveAsHadoopFile(outputFolderPath, Text.class, PngImageWritable.class, PngImageOutputFormat.class);
		sc.stop();
	}

	public static String getFileName(String pathStr) {
		String[] tokens = pathStr.split("/");
		// return tokens[tokens.length - 1];.split("_")[2]
		return tokens[tokens.length - 1];
	}
	
	public static String getFileLocationOnImage(String pathStr){
		String[] tokens = pathStr.split("/");
		String[] imgTokens = tokens[tokens.length - 1].split("_");
		return imgTokens[0] + "_" + imgTokens[1];
	}

}
