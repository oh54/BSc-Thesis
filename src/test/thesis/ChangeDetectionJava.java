package test.thesis;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChangeDetectionJava {

	public static void main(String[] args) throws IOException {
		String path1 = "/home/oskar/workspace/BSc-Thesis/input/S1A_EW_GRDM_1SDH_20141203T044116_20141203T044216_003550_0042ED_C698.SAFE/measurement/s1a-ew-grd-hh-20141203t044116-20141203t044216-003550-0042ed-001.tiff";
		String path2 = "/home/oskar/workspace/BSc-Thesis/input/S1A_EW_GRDM_1SDH_20141227T044116_20141227T044216_003900_004AE5_7EF0.SAFE/measurement/s1a-ew-grd-hh-20141227t044116-20141227t044216-003900-004ae5-001.tiff";
		String path3 = "/home/oskar/workspace/BSc-Thesis/input/S1A_EW_GRDM_1SDH_20150309T044114_20150309T044214_004950_006310_5444.SAFE/measurement/s1a-ew-grd-hh-20150309t044114-20150309t044214-004950-006310-001.tiff";

		BufferedImage img1 = ImageIO.read(new File(path1));
		BufferedImage img2 = ImageIO.read(new File(path2));
		BufferedImage img3 = ImageIO.read(new File(path3));

		int w = Integer.min(img1.getWidth(), img3.getWidth());
		int h = Integer.min(img1.getHeight(), img3.getHeight());

		BufferedImage processedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		System.out.println(img1.getWidth() + " by " + img1.getHeight());
		System.out.println(img2.getWidth() + " by " + img2.getHeight());
		System.out.println(img3.getWidth() + " by " + img3.getHeight());

		System.out.println("STARTING PROCCESSING");

		for (int x = 0; x < processedImg.getWidth(); x++) {
			for (int y = 0; y < processedImg.getHeight(); y++) {
				int r1 = (new Color(img1.getRGB(x, y))).getRed();
				int g2 = (new Color(img2.getRGB(x, y))).getGreen();
				int b3 = (new Color(img3.getRGB(x, y))).getBlue();
				processedImg.setRGB(x, y, (new Color(r1, g2, b3).getRGB()));
				//processedImg.setRGB(x, y, (new Color(r1, g2, (r1 + g2) / 2).getRGB()));
			}
			if (x % 100 == 0) {
				System.out.println(x);
			}
		}

		System.out.println("DONE");
		File outputFile = new File("/home/oskar/workspace/BSc-Thesis/output/COMBINED.png");

		ImageIO.write(processedImg, "png", outputFile);

	}
}
