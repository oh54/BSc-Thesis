package test.thesis;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SplitTest {

	public static void main(String[] args) throws IOException {
		//String inputFolder = "/home/oskar/workspace/BSc-Thesis/input/sar";
		//String fileName = inputFolder + "/sarpic.tif";
		//String fileName = "/home/oskar/workspace/BSc-Thesis/input/S1A_IW_SLC__1SDV_20150501T160356_20150501T160424_005730_0075B6_8204.SAFE/measurement/s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";
		
		String fileName = args[0];

		//File inputFile = new File(fileName);
		//ImageInputStream stream = ImageIO.createImageInputStream(inputFile);
		
		BufferedImage tiffImg = ImageIO.read(new File(fileName));
		//BufferedImage tiffImg = ImageIO.read(stream);
		
		int n = tiffImg.getHeight();
		int m = tiffImg.getWidth();

		//System.out.println(n + " by " + m + " image");
		
		System.out.println(n);
		System.out.println(m);
		
		//System.out.println(tiffImg.getType());
		
		//ImageIO.write(tiffImg, "tiff", new File("output/" + "CHECKTIFF.tiff"));
		

		
		//writeSplits(tiffImg, 4, 4, "output/");

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

				//imgs[count] = new BufferedImage(splitWidth, splitHeight, BufferedImage.TYPE_BYTE_BINARY);

				imgs[count++] = img.getSubimage(splitWidth * y, splitHeight * x, splitWidth, splitHeight);
				// subimage?
				
				/*
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(img, 0, 0, splitWidth, splitHeight, splitWidth * y, splitHeight * x, splitWidth * y + splitWidth, splitHeight * x
						+ splitHeight, null);
				gr.dispose();
				*/
			}
		}
		for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "tiff", new File(outputFolder + "split" + i + ".tiff"));
		}
	}

}
