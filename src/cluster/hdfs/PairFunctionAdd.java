package cluster.hdfs;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.hadoop.io.Text;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;

import scala.Tuple2;

public class PairFunctionAdd implements PairFunction<Tuple2<String, PortableDataStream>, Text, TiffImageWritable> {
	Integer hr;
	Integer wr;

	public PairFunctionAdd(Integer wr, Integer hr) {
		this.hr = hr;
		this.wr = wr;
	}

	@Override
	public Tuple2<Text, TiffImageWritable> call(Tuple2<String, PortableDataStream> t) throws Exception {

		BufferedImage origImg = ImageIO.read(t._2.open());

		System.out.println("1");
		BufferedImage processedImg = deepCopy(origImg);
		System.out.println("2");
		BufferedImage processedWindow = processedImg.getSubimage(wr, hr, origImg.getWidth() - 2 * wr, origImg.getHeight() - 2 * hr);
		System.out.println("3");
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
