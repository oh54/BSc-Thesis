package cluster.algos;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

import scala.Tuple2;
import cluster.hdfs.TiffImageOutputFormat;
import cluster.hdfs.TiffImageWritable;


public class NoiseReductionMean {

	public static void main(String[] args) throws Exception {
		String appName = "SparkNoiseReductionMean";
		if (args.length < 4) {
			System.err.println("Usage: " + appName + " <inputfolder> <outputfolder> <width overlap> <height overlap>");
			System.exit(1);
		}
		SparkConf conf = new SparkConf().setAppName(appName).setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		String inputFolderPath = args[0];
		String outputFolderPath = args[1];
		Integer wr = Integer.parseInt(args[2]);
		Integer hr = Integer.parseInt(args[3]);
		Class<Text> keyClass = Text.class;
		Class<TiffImageWritable> valueClass = TiffImageWritable.class;

		JavaPairRDD<String, PortableDataStream> input = sc.binaryFiles(inputFolderPath, 16);
		
		JavaPairRDD<Text, TiffImageWritable> imgs = input.mapToPair(t -> {
			BufferedImage origImg = ImageIO.read(t._2.open());
			BufferedImage processedImg = deepCopy(origImg);
			BufferedImage processedWindow = processedImg.getSubimage(wr, hr, origImg.getWidth() - 2 * wr, origImg.getHeight() - 2 * hr);
			
			WritableRaster raster = processedWindow.getRaster();
			int origWidth = origImg.getWidth();
			int origHeight = origImg.getHeight();
			for (int x = wr; x < origWidth - wr; x++) {
				for (int y = hr; y < origHeight - hr; y++) {
					BufferedImage window = origImg.getSubimage(x - wr, y - hr, 2 * wr + 1, 2 * hr + 1);
					int[] gVal = new int[1];
					gVal[0] = getMedian(wr, hr, window);
					raster.setPixel(x, y, gVal);
				}
			}
			return new Tuple2(new Text(t._1), new TiffImageWritable(processedImg));
		});
		
		

		imgs.saveAsHadoopFile(outputFolderPath, keyClass, valueClass, TiffImageOutputFormat.class);
		/*
		FileSystem fs= FileSystem.get(sc.hadoopConfiguration());
		imgs.foreach(t ->{
			Path p = new Path(t._1.toString());
			FSDataOutputStream stream = fs.create(p);
			
			t._2.write(stream);
			
		});
		*/
		
		sc.stop();
	}

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static int getMedian(int wr, int hr, BufferedImage window) {
		int w = window.getWidth();
		int h = window.getHeight();
		int[] gs = new int[(2 * wr + 1) * (2 * hr + 1) - 1];
		int count = 0;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (!(x == wr + 1 && y == wr + 1)) {
					Raster r = window.getRaster();
					int g = r.getSample(x, y, 0);
					gs[count++] = g;
				}
			}
		}
		Arrays.sort(gs);
		int median = gs.length % 2 == 0 ? gs[gs.length / 2 - 1] : gs[gs.length / 2];
		return median;
	}

}
