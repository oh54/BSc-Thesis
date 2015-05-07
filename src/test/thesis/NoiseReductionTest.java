package test.thesis;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class NoiseReductionTest {
	public static void main(String[] args) throws IOException {
		//String inputFolder = "/home/oskar/workspace/BSc-Thesis/output/";
		//String imgName = "split1.tiff";

		//String fileName = "/home/oskar/workspace/BSc-Thesis/input/S1A_IW_SLC__1SDV_20150501T160356_20150501T160424_005730_0075B6_8204.SAFE/measurement/s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";

		//String fileName = "/home/oskar/workspace/BSc-Thesis/output/hdfs/0_0_s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";
		
		String fileName = "/home/oskar/workspace/BSc-Thesis/input/S1A_IW_SLC__1SDV_20150501T160356_20150501T160424_005730_0075B6_8204.SAFE/measurement/s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";

		BufferedImage origImg = ImageIO.read(new File(fileName));
		int hr = 1;
		int wr = 1;
		int origWidth = origImg.getWidth();
		int origHeight = origImg.getHeight();
		
		BufferedImage processedImg = deepCopy(origImg);
		
		WritableRaster raster = processedImg.getRaster();

		System.out.println("IMAGE READ AND COPIED");

		for (int x = wr; x < origWidth - wr; x++) {
			for (int y = hr; y < origHeight - hr; y++) {
				BufferedImage window = origImg.getSubimage(x - wr, y - hr, 2 * wr + 1, 2 * hr + 1);
				int[] gVal = new int[1];
				gVal[0] = getMedian(wr, hr, window);
				raster.setPixel(x, y, gVal);
			}
			if ((x % 100) == 0){
				System.out.println(x);
			}
			
		}

		File outputFile = new File("/home/oskar/workspace/BSc-Thesis/output/noise_test.tiff");

		ImageIO.write(processedImg, "tiff", outputFile);
		
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

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
