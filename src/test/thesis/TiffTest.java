package test.thesis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TiffTest {

	public static void main(String[] args) throws IOException {
		BufferedImage tiffImg = ImageIO.read(new File("input/sarpic.tif"));

		int n = tiffImg.getHeight();
		int m = tiffImg.getWidth();
		
		
		System.out.println(n + " by " + m + " image");	
		
		File outputFile = new File("output/outPic.tif");
		
		ImageIO.write(tiffImg, "tif", outputFile);
	}

}
