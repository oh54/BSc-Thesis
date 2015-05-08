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
		if (args.length < 2) {
			System.err.println("Usage: " + appName + " <inputfolder> <outputfolder>");
			System.exit(1);
		}
		SparkConf conf = new SparkConf().setAppName(appName).setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		String inputFolderPath = args[0];
		String outputFolderPath = args[1];

		JavaPairRDD<String, PortableDataStream> allInputFiles = sc.binaryFiles(inputFolderPath, 16);

		JavaRDD<String> paths = allInputFiles.map(tuple -> new String(tuple._1));
		List<String> pathsList = paths.collect();
		
		String fstFileName = getFileName(pathsList.get(0));


		//System.out.println(Arrays.toString(pathsList.toArray()));

		JavaPairRDD<Text, TiffImageWritable> imgs = allInputFiles.mapToPair(t -> {
			BufferedImage origImg = ImageIO.read(t._2.open());
			return new Tuple2(new Text(t._1), new TiffImageWritable(origImg));
		});

		JavaPairRDD<Text, TiffImageWritable> imgs1 = imgs.filter(t -> {
			return getFileName(t._1.toString()).equals(fstFileName);
		});
		

		JavaPairRDD<Text, TiffImageWritable> imgs2 = imgs.filter(t -> {
			return !getFileName(t._1.toString()).equals(fstFileName);
		});
		
		/*
		JavaPairRDD<Text, TiffImageWritable> imgs1Names = imgs1.mapToPair(t -> {
			return new Tuple2<Text, TiffImageWritable>(new Text(getFileLocationOnImage(t._1.toString())), t._2);
		});
		
		JavaPairRDD<Text, TiffImageWritable> imgs2Names = imgs1.mapToPair(t -> {
			return new Tuple2<Text, TiffImageWritable>(new Text(getFileLocationOnImage(t._1.toString())), t._2);
		});
		*/
		JavaPairRDD<String, TiffImageWritable> imgs1Names = imgs1.mapToPair(t -> {
			return new Tuple2("1", t._2);

		});
		
		JavaPairRDD<String, TiffImageWritable> imgs2Names = imgs1.mapToPair(t -> {
			return new Tuple2("1", t._2);
			//return new Tuple2(new Text(t._1), new TiffImageWritable(processedImg));

		});
		
		
		JavaPairRDD<String, Tuple2<TiffImageWritable, TiffImageWritable>> joinedImgs = imgs1Names.join(imgs1Names);

		

		JavaPairRDD<Text, PngImageWritable> proccessedImgs = joinedImgs.mapToPair(t -> {
			BufferedImage img1 = t._2._1.getImg();
			BufferedImage img2 = t._2._2.getImg();
			
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
		//imgs1.saveAsHadoopFile(outputFolderPath, Text.class, TiffImageWritable.class, TiffImageOutputFormat.class);
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
