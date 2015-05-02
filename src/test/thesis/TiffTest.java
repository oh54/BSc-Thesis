package test.thesis;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import parquet.hadoop.api.WriteSupport;

public class TiffTest {

	public static void main(String[] args) throws IOException {
		String inputFolder = "/home/oskar/workspace/BSc-Thesis/input/sar";

		BufferedImage tiffImg = ImageIO.read(new File(inputFolder + "/sarpic.tif"));

		int n = tiffImg.getHeight();
		int m = tiffImg.getWidth();

		System.out.println(n + " by " + m + " image");
		
		
		System.out.println(tiffImg.getType());
		
		writeSplits(tiffImg, 4, 4, "output/");

		//File outputFile = new File("output/outPic.tif");

		//ImageIO.write(tiffImg, "tif", outputFile);
	}

	public static void writeSplits(BufferedImage img, int nrows, int ncols, String outputFolder) throws IOException {

		int width = img.getWidth();
		int height = img.getHeight();

		int nsplits = nrows * ncols;

		int splitWidth = width / ncols;
		int splitHeight = height / nrows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[nsplits];
		for (int x = 0; x < nrows; x++) {
			for (int y = 0; y < ncols; y++) {

				imgs[count] = new BufferedImage(splitWidth, splitHeight, img.getType());

				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(img, 0, 0, splitWidth, splitHeight, splitWidth * y, splitHeight * x, splitWidth * y + splitWidth, splitHeight * x
						+ splitHeight, null);
				gr.dispose();
			}
		}
		for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "tif", new File(outputFolder + "split" + i + ".tif"));
		}
	}

}
