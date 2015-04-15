package test.thesis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TiffTest {

	public static void main(String[] args) throws IOException {
		BufferedImage tiffImg = ImageIO.read(new File("input/sarpic.tif"));

		int n2 = tiffImg.getHeight();
		int m2 = tiffImg.getWidth();
		System.out.println(n2 + " by " + m2 + " image");
	}

}
