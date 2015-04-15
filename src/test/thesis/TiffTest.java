package test.thesis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TiffTest {

	public static void main(String[] args) throws IOException {

		BufferedImage jpegImg = ImageIO.read(new File("input/pic.jpg"));

		int n1 = jpegImg.getHeight();
		int m1 = jpegImg.getWidth();

		int type = jpegImg.getType();

		BufferedImage newImg = new BufferedImage(m1, n1, type);

		System.out.println(n1 + " by " + m1 + " image with type " + type);

		try {
			BufferedImage tiffImg = ImageIO.read(new File("input/sarpic.tif"));

			int n2 = tiffImg.getHeight();
			int m2 = tiffImg.getWidth();
			System.out.println(n2 + " by " + m2 + " image");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
