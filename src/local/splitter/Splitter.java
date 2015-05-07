package local.splitter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Splitter {

	public static void main(String[] args) throws IOException {
		String path = "/home/oskar/workspace/BSc-Thesis/input/S1A_IW_SLC__1SDV_20150501T160356_20150501T160424_005730_0075B6_8204.SAFE/measurement/s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";
		//String path = "/home/oskar/workspace/BSc-Thesis/input/sar/sarpic.tif";
		//String path = "/home/oskar/workspace/BSc-Thesis/input/pic.jpg";
		File imgFile = new File(path);
		BufferedImage tiffImg = ImageIO.read(imgFile);
		writeSplits(tiffImg, 4, 4, "output/hdfs/",imgFile.getName(), 10, 10);
	}

	public static void writeSplits(BufferedImage img, int nrows, int ncols, String outputFolder, String inImgName, int wr, int hr) throws IOException {
		int splitWidth = img.getWidth() / ncols;
		int splitHeight = img.getHeight() / nrows;
		int count = 0;
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				DiscreteRectangleArea dra = getArea(i, j, nrows, ncols, wr, hr, splitWidth, splitHeight, splitWidth * j, splitHeight * i);
				BufferedImage outImg = img.getSubimage(dra.getUpperLeftX(), dra.getUpperLeftY(), dra.getWidth(), dra.getHeight());
				String outImgName = i + "_" + j + "_" + inImgName;
				ImageIO.write(outImg, "tiff", new File(outputFolder + outImgName));
				System.out.println(++count);
			}
		}
	}
	public static DiscreteRectangleArea getArea(int i, int j, int nrows, int ncols,  int wr, int hr, int splitWidth, int splitHeight, int baseUpperLeftX, int baseUpperLeftY){
		DiscreteRectangleArea dra = new DiscreteRectangleArea(splitWidth, splitHeight, wr, hr, baseUpperLeftX, baseUpperLeftY);
		if (i == 0) dra.setTopConstraint();
		if (i == nrows - 1) dra.setBottomConstraint();
		if (j == 0) dra.setLeftConstraint();
		if (j == ncols - 1) dra.setRightConstraint();
		dra.conformToConstraints();
		return dra;
	}
	

}
